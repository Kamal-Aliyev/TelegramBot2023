package com.example.telegrambot2023.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AfterSignInResponseDto {
    private String token;

    private String email;

    private String role;


    @Override
    public String toString() {
        return "token = " + token + '\n' +
                "email = " + email;

    }
}
