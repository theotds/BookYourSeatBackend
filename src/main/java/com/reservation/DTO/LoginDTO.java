package com.reservation.DTO;

import lombok.Data;

@Data
public class LoginDTO {
    private String username;
    private String password;
    private String role;
}