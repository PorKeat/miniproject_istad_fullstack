package model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
@AllArgsConstructor
@Data
public class Product {
    private UUID uuid; // Universally Unique Identifier
    private int id;
    private String p_name;
    private double price;
    private int qty;
    private String category;


    public Product(UUID uuid, int id, String name, double price, String category) {
        this.uuid = uuid;
        this.id = id;
        this.p_name = p_name;
        this.price = price;
        this.category = category;

    }

    @Override
    public String toString() {
        return "• " + p_name + " | $" + price + " | Qty: " + qty + " | UUID: " + uuid;
    }
}