package model.service;

import model.dto.OrderResponseDto;

public interface OrderService {
    OrderResponseDto placeOrder(Integer userId);
}
