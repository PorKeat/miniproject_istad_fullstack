package model.dto;

public record ProductCreateDto(
        String pName,
        Float price,
        Integer qty
) {

}
