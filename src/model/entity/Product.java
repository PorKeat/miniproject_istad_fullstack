package model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private Integer id;
    private String name;
    private double price;
    private boolean isDeleted;
    private String uuid;
    private String category;
}
