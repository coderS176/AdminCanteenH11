package com.example.adminch11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private ProgressBar progressBar;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userId;
    private String TAG = "Login Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser()!=null){
            openDashboardActivity();
        }
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input from EditText fields
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                progressBar.setVisibility(View.GONE);
                // Check if the email and password match the predefined values
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            userId = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userId);
                            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Log.d(TAG,"onSuccess:"+documentSnapshot.getData());
                                    if(documentSnapshot.getBoolean("is_admin")!=null){
                                        Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                        openDashboardActivity();
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this,"Not a Registered Admin.",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                        else {
                            Toast.makeText(MainActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    // Method to open MenuActivity
    private void openDashboardActivity() {
        Intent intent = new Intent(MainActivity.this, AdminDashboardActivity.class);
        startActivity(intent);
        finish(); // Close LoginActivity
    }
}
