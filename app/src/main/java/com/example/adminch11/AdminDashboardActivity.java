package com.example.adminch11;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Find buttons by their IDs
        Button btnAdd = findViewById(R.id.btnAddProduct);
        Button btnEdit = findViewById(R.id.btnEditProduct);
        Button btnDelete = findViewById(R.id.btnDeleteProduct);
        Button btnManageOrders = findViewById(R.id.btnManageOrders);
        Button btnAnalyzeSales = findViewById(R.id.btnAnalyzeSales);
        Button btnCheckHistory = findViewById(R.id.btnCheckHistory);

        // Set OnClickListener for each button
        btnAdd.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnManageOrders.setOnClickListener(this);
        btnAnalyzeSales.setOnClickListener(this);
        btnCheckHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Handle button clicks
        if (v.getId() == R.id.btnAddProduct) {
            startActivity(new Intent(AdminDashboardActivity.this, AddProductActivity.class));
        }
        else if (v.getId() == R.id.btnEditProduct) {
            startActivity(new Intent(AdminDashboardActivity.this, EditProductActivity.class));
        }
        else if (v.getId() == R.id.btnManageOrders) {
            startActivity(new Intent(AdminDashboardActivity.this, OrderManagementActivity.class));
        } else if (v.getId() == R.id.btnAnalyzeSales) {
            startActivity(new Intent(AdminDashboardActivity.this, ManageSalesActivity.class));
        } else if (v.getId() == R.id.btnCheckHistory) {
            startActivity(new Intent(AdminDashboardActivity.this, OrderHistoryActivity.class));
        }
    }
}
