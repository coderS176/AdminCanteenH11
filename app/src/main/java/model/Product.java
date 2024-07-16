package model;

// Product.java
import java.io.Serializable;

public class Product implements Serializable {

    private String productId;
    private String productName;
    private String description;
    private String status;
    private String type;
    private int price;
    private int capacity;
    private int cookingTime;
    private String imageUrl;

    public Product(){

    }
    // Constructor
    public Product(String productId, String productName, String description, String availableStatus, String type, int price, int applianceCapacity, int cookingTime, String imageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.status = availableStatus;
        this.type = type;
        this.price = price;
        this.capacity = applianceCapacity;
        this.cookingTime = cookingTime;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvailableStatus() {
        return status;
    }

    public void setAvailableStatus(String availableStatus) {
        this.status = availableStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getApplianceCapacity() {
        return capacity;
    }

    public void setApplianceCapacity(int applianceCapacity) {
        this.capacity = applianceCapacity;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
