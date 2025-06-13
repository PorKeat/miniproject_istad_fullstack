package model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private Integer id;
    private Integer userId;
    private Date orderDate;
    private Double totalPrice;
    private String orderCode;
}
