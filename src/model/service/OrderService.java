package model.service;

import model.entity.Order;

import java.util.List;

public interface OrderService {
    Order placeOrderAndReturnFullInfo(int userId);
    List<Order> getOrderHistory(int userId);
}
