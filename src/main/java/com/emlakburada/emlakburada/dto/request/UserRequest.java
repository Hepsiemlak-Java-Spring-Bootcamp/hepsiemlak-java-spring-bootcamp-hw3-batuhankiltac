package com.emlakburada.emlakburada.dto.request;

import lombok.Data;

@Data
public class UserRequest {
    private int userId;
    private String name;
    private String email;
}