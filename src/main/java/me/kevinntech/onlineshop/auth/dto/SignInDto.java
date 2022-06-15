package me.kevinntech.onlineshop.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInDto {

    private String username;

    private String password;

    @Builder
    public SignInDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

}