package controller;

import View.OrderView;
import model.dto.OrderProductResponseDto;
import model.dto.OrderResponseDto;
import model.service.OrderServiceImpl;


public class OrderController {
    private final OrderServiceImpl orderService = new OrderServiceImpl();
    private final OrderView orderView = new OrderView();
    public void placeOrder(Integer userId) {
        try {
            OrderResponseDto order = orderService.placeOrder(userId);
            orderView.displayOrderDetails(order);
        } catch (Exception e) {
            System.out.println("[!] Error occurred while place order: "+e.getMessage());
        }
    }

}
