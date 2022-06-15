package me.kevinntech.onlineshop.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class SignInRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Length(min = 8, max = 50)
    private String password;

    public SignInDto toDto() {
        return SignInDto.builder()
                .username(username)
                .password(password)
                .build();
    }

}