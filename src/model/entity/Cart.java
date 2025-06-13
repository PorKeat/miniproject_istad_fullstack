package model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    private Integer userId;
    private Integer productId;
    private Integer qty;
    private Date addedAt;
    private String productName;
    private String username;
    private Double price;
}
