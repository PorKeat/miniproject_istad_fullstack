package model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Integer id;
    private String userName;
    private String email;
    private String password;
    private Boolean isDeleted;
    private String uUuid;
    private String role;
}
