package com.example.demo.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JWTTokenSuccessResponse {
    private boolean success;
    private String token;

    public JWTTokenSuccessResponse(boolean success, String token) {
        this.success = success;
        this.token = token;
    }
}
