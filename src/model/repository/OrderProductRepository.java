package model.repository;

import model.entity.OrderProduct;

import java.util.List;

public interface OrderProductRepository {
    void createBatch(List<OrderProduct> orderProducts);
}
