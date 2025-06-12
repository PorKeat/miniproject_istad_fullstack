package model.dto;

public record ProductResponDto(
        String pName,
        Float price,
        Integer qty,
        boolean isDeleted,
        String pUuid

) {

}
