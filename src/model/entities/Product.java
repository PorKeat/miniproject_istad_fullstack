package model.entities;


public class Product {
    private String uuid;
    private int id;
    private String p_name;
    private Double price;
    private int qty;
    private String category;

    public Product() {}

    public Product(String uuid, int id, String p_name, Double price, String category) {
        this.uuid = uuid;
        this.id = id;
        this.p_name = p_name;
        this.price = price;
        this.category = category;

    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format("%-36s %-20s $%-10.2f %-5d", uuid, p_name, price, qty);

    }
}
