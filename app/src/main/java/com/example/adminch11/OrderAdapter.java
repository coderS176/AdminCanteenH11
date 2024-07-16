package com.example.adminch11;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import model.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewOrderName;
        private TextView textViewOrderStatus;
        private TextView textViewWaitingTime;
        private RecyclerView recyclerViewItems;
        private ItemAdapter itemAdapter;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderName = itemView.findViewById(R.id.textViewOrderName);
            textViewOrderStatus = itemView.findViewById(R.id.textViewOrderStatus);
            textViewWaitingTime = itemView.findViewById(R.id.textViewWaitingTime);
            recyclerViewItems = itemView.findViewById(R.id.recyclerViewItems);
            recyclerViewItems.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

            // Initialize ItemAdapter and set it to inner RecyclerView
            itemAdapter = new ItemAdapter();
            recyclerViewItems.setAdapter(itemAdapter);
        }

        public void bind(Order order) {
            textViewOrderName.setText(order.getOrderId());
            textViewOrderStatus.setText(order.getStatus());
            textViewWaitingTime.setText(String.valueOf(order.getWaitingTime()));

            // Populate list of items in inner RecyclerView
            itemAdapter.setItemList(order.getItemList());
            itemAdapter.notifyDataSetChanged();
        }
    }
}
