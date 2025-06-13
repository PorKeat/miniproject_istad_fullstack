package model.service;

import model.entity.Order;

public interface OrderService {
    Order placeOrder(int userId);
}
