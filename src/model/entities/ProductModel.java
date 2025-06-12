package model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductModel {
    private Integer id;
    private String pName;
    private Float price;
    private Integer qty;
    private boolean isDeleted;
    private String pUuid;
}
