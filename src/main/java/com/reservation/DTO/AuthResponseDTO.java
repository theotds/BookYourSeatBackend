package com.reservation.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthResponseDTO {
    private String token;
    private String role;
    private Long userId;

    public AuthResponseDTO(String token, String role, Long userId) {
        this.token = token;
        this.role = role;
        this.userId = userId;

    }

}
