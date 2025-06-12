package model.repository;

import model.entities.ProductModel;

public interface ProductRepository {
    ProductModel findByUUID(String uuid);
}
