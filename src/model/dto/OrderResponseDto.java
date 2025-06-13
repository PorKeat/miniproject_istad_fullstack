package model.dto;

import lombok.Builder;

import java.sql.Date;
import java.util.List;

@Builder
public record OrderResponseDto(
        String orderCode,
        Double totalPrice,
        Date orderDate,
        List<OrderProductResponseDto> items
) {
}
