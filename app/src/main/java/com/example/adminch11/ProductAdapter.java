package com.example.adminch11;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        Picasso.get().load(product.getImageUrl()).into(holder.imageViewProduct);
        holder.textViewItemName.setText(product.getProductName());
        holder.textViewItemPrice.setText("Price: Rs" + product.getPrice());

        // Set click listener for the "Add to Cart" button
//        holder.buttonSelectProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // When the button is clicked, open the EditProductActivity
//                Intent intent = new Intent(context, EditProductActivity.class);
//                // Pass the selected product details to the EditProductActivity
//                intent.putExtra("product", product);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageViewProduct;
        public TextView textViewItemPrice,textViewItemName;
        public Button buttonSelectProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageMenuItem);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewItemPrice = itemView.findViewById(R.id.textViewItemPrice);
            buttonSelectProduct = itemView.findViewById(R.id.buttonSelectProduct);
        }
    }
}
