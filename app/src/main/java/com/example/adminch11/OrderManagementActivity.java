package com.example.adminch11;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import model.Order;
import java.util.List;
import model.Item;

public class OrderManagementActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize OrderAdapter and set it to RecyclerView
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderList);
        recyclerView.setAdapter(orderAdapter);
        fStore = FirebaseFirestore.getInstance();

        // Simulate loading orders from database
        loadPendingOrdersFromDatabase();

        // Button to change status of selected order
//        Button buttonChangeStatus = findViewById(R.id.buttonChangeStatus);
//        buttonChangeStatus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Implement logic to change status of selected order
//                Toast.makeText(ManageOrdersActivity.this, "Change Status Button Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });

        // Button to update waiting time
//        Button buttonUpdateWaitingTime = findViewById(R.id.buttonUpdateWaitingTime);
//        buttonUpdateWaitingTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Implement logic to update waiting time of selected order
//                EditText editTextWaitingTime = findViewById(R.id.editTextWaitingTime);
//                String waitingTimeStr = editTextWaitingTime.getText().toString();
//                int waitingTime = Integer.parseInt(waitingTimeStr);
//                Toast.makeText(ManageOrdersActivity.this, "Update Waiting Time Button Clicked: " + waitingTime + " minutes", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    // Method to simulate loading orders from database
    private void loadPendingOrdersFromDatabase() {
        CollectionReference ordersRef = fStore.collection("orders");

        // Query orders where status is "pending"
        Query pendingOrdersQuery = ordersRef.whereEqualTo("status", "pending");

        pendingOrdersQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot orderDoc : task.getResult().getDocuments()) {
                        Order order = orderDoc.toObject(Order.class);
                        if (order != null) {
                            orderList.add(order);
                        }
                    }
                    orderAdapter.notifyDataSetChanged();
                } else {  
                    Toast.makeText(OrderManagementActivity.this, "Failed to fetch pending orders", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}