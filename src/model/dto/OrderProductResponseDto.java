package model.dto;

import lombok.Builder;

@Builder
public record OrderProductResponseDto(
//        Integer qty,
//        String pName,
//        Double pPrice
        Integer orderId,
        Integer pId,
        String pName,
        Double price,
        Integer qty,
        Double subTotal

) {
}
