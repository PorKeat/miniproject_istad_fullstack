package model.repository;

import model.dto.OrderResponseDto;
import model.entity.Order;

import java.util.List;

public interface OrderRepository {
    Order createOrder(Order order);
}
