package com.example.adminch11;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.UploadTask;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddProductActivity extends AppCompatActivity {

    private Uri imageUri; // Holds the URI of the selected image
    private EditText editTextProductName, editTextDescription, editTextPrice, editTextImage,editTextCookingTime,editTextApplianceCapacity;
    private ImageView imageViewPreview;
    private Button buttonAddProduct;
    private Spinner type, availableStatus;
    private boolean isImageSelectionInProgress = false;
    private ActivityResultLauncher<Void> pickImageLauncher;
    private String TAG ="AddProductActivity";
    private FirebaseFirestore fStore;
    private FirebaseStorage fStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Initialize UI elements
        editTextProductName = findViewById(R.id.editTextProductName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextImage = findViewById(R.id.editTextImage);
        editTextCookingTime = findViewById(R.id.editTextCookingTime);
        editTextApplianceCapacity = findViewById(R.id.editTextApplianceCapacity);
        imageViewPreview = findViewById(R.id.imageViewPreview);
        buttonAddProduct = findViewById(R.id.buttonAddProduct);
        type = findViewById(R.id.spinnerType);
        availableStatus = findViewById(R.id.spinnerAvailableStatus);
        fStore = FirebaseFirestore.getInstance();
        fStorage = FirebaseStorage.getInstance();

        // Initialize the pickImageLauncher with the PickImageContract
        pickImageLauncher = registerForActivityResult(new PickImageContract(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    // Set the selected image URI to imageViewPreview for preview
//                    imageViewPreview.setImageURI(result);
                    // Make the imageViewPreview visible
//                    imageViewPreview.setVisibility(View.VISIBLE);
                    // Store the image URI
                    imageUri = result;
                    // Optionally, enable the imageViewBrowse again for future selections
                    isImageSelectionInProgress = false;
                }
            }
        });

        // Set click listener for imageViewBrowse (browse icon)
        editTextImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Disable the ImageView to prevent multiple clicks
                if (!isImageSelectionInProgress) {
                    isImageSelectionInProgress = true;
                    // Launch image picker using the pickImageLauncher
                    pickImageLauncher.launch(null);
                }
            }
        });
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct(v);
            }
        });
    }


    // Method to handle the button click event for adding the product
    public void addProduct(View view) {
        // Validate inputs
        if (editTextProductName.getText().toString().isEmpty() ||
                editTextDescription.getText().toString().isEmpty() ||
                editTextPrice.getText().toString().isEmpty() ||
                imageUri == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get values from UI elements
        String productName = editTextProductName.getText().toString();
        String description = editTextDescription.getText().toString();
        int price = Integer.parseInt(editTextPrice.getText().toString());
        int cookingTime = Integer.parseInt(editTextCookingTime.getText().toString());
        int capacity = Integer.parseInt(editTextApplianceCapacity.getText().toString());
        String pType = type.getSelectedItem().toString();
        String status = availableStatus.getSelectedItem().toString();

        // Get a reference to the Firebase Storage location
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("product_images/" + UUID.randomUUID().toString());

        // Upload the image to Firebase Storage
        storageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Image uploaded successfully
                        // Get the download URL of the uploaded image
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUri) {
                                // Add other product data along with the image URL to Firestore
                                addProductToFirestore(productName, description, price, cookingTime, capacity, pType, status, downloadUri.toString());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error uploading image
                        Log.e(TAG, "Error uploading image", e);
                        // Show error message
                        Toast.makeText(AddProductActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addProductToFirestore(String productName, String description, int price, int cookingTime, int capacity, String pType, String status, String imageUrl) {
        // Create a Map to represent the data
        Map<String, Object> product = new HashMap<>();
        product.put("productName", productName);
        product.put("description", description);
        product.put("price", price);
        product.put("cookingTime", cookingTime);
        product.put("capacity", capacity);
        product.put("type", pType);
        product.put("status", status);
        product.put("imageUrl", imageUrl); // Add image URL

        // Add data to Firestore
        FirebaseFirestore.getInstance().collection("products").add(product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Data added successfully
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        // Show success message
                        Toast.makeText(AddProductActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();

                        // Clear input fields
                        editTextProductName.getText().clear();
                        editTextDescription.getText().clear();
                        editTextPrice.getText().clear();
                        imageViewPreview.setImageURI(null); // Clear image preview
                        imageViewPreview.setVisibility(View.GONE); // Hide image preview
                        imageUri = null; // Reset image URI
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error adding data
                        Log.w(TAG, "Error adding document", e);
                        // Show error message
                        Toast.makeText(AddProductActivity.this, "Failed to add product", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    // PickImageContract for selecting an image
    public class PickImageContract extends ActivityResultContract<Void, Uri> {
        @Override
        public Intent createIntent(Context context, Void input) {
            return new Intent(Intent.ACTION_PICK).setType("image/*");
        }

        @Override
        public Uri parseResult(int resultCode, Intent intent) {
            if (resultCode == RESULT_OK && intent != null) {
                return intent.getData();
            } else {
                return null;
            }
        }
    }
}
