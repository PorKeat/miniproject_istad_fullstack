package model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderProduct {
    private Integer orderId;
    private Integer productId;
    private Integer qty;
}
