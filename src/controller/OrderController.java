package controller;

import model.dto.OrderProductResponseDto;
import model.dto.OrderResponseDto;
import model.service.OrderServiceImpl;


public class OrderController {
    private final OrderServiceImpl orderService = new OrderServiceImpl();
    public void placeOrder(Integer userId) {
        try {
            OrderResponseDto order = orderService.placeOrder(userId);
            displayOrderDetails(order);
        } catch (Exception e) {
            System.out.println("[!] Error occurred while place order: "+e.getMessage());
        }
    }

}
