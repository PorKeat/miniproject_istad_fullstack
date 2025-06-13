package model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int id;
    private int userId;
    private Date orderDate;
    private double totalPrice;
    private String orderCode;
    private List<OrderProduct> orderProducts;
}
