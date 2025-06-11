package model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;
@AllArgsConstructor
@Data

public class Product {
    private UUID uuid;
    private int id;
    private String p_name;
    private double price;
    private int qty;
    private String category;
    public Product(UUID uuid, int id, String p_name, double price, String category) {
        this.uuid = uuid;
        this.id = id;
        this.p_name = p_name;
        this.price = price;
        this.category = category;

    }
    @Override
    public String toString() {
        return String.format("%-36s %-20s $%-10.2f %-5d", uuid.toString(), p_name, price, qty);

    }
}
