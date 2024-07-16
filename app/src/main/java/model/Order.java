package model;
import java.util.List;

public class Order {
    private String orderId;
    private String status;
    private int waitingTime;
    private List<Item> itemList;

    public Order() {
        // Default constructor required for Firebase
    }

    public Order(String orderId, String status, int waitingTime, List<Item> itemList) {
        this.orderId = orderId;
        this.status = status;
        this.waitingTime = waitingTime;
        this.itemList = itemList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
