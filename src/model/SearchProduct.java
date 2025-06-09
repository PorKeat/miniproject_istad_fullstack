package model;

public class SearchProduct {
    public int id;
    private String p_name;
    private double price;
    private boolean is_deleted;
    private String p_uuid;

    public SearchProduct(String p_name, double price, boolean is_deleted, String p_uuid) {
        this.p_name = p_name;
        this.price = price;
        this.is_deleted = is_deleted;
        this.p_uuid = p_uuid;
    }

    public int getId() { return id; }
    public String getP_name() { return p_name; }
    public double getPrice() { return price; }
    public boolean isIs_deleted() { return is_deleted; }
    public String getP_uuid() { return p_uuid; }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Price: $%.2f, UUID: %s, Deleted: %b",
                id, p_name, price, p_uuid, is_deleted);
    }
}