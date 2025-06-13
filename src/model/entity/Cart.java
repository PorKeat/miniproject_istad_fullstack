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
    private int userId;
    private int productId;
    private int qty;
    private Date addedAt;
}
