package model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class OrderProduct {
    private int orderId;
    private int productId;
    private int qty;
    private String productName;
    private double productPrice;
}
