package model;

public class SearchProduct {
    public int id;
    private String p_name;
    private double price;
    private int qty;
    private boolean is_deleted;
    private String p_uuid;

    public SearchProduct(String p_name, double price, int qty, boolean is_deleted, String p_uuid) {
        this.id = -1; // Placeholder, will be set by database auto-increment
        this.p_name = p_name;
        this.price = price;
        this.qty = qty;
        this.is_deleted = is_deleted;
        this.p_uuid = p_uuid;
    }

    public int getId() { return id; }
    public String getP_name() { return p_name; }
    public double getPrice() { return price; }
    public int getQty() { return qty; }
    public boolean isIs_deleted() { return is_deleted; }
    public String getP_uuid() { return p_uuid; }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Price: $%.2f, Quantity: %d, UUID: %s, Deleted: %b",
                id, p_name, price, qty, p_uuid, is_deleted);
    }
}
