package me.kevinntech.onlineshop.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kevinntech.onlineshop.domain.auth.annotation.UniqueEmail;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ValidationEmailRequest {

    @NotBlank
    @UniqueEmail
    private String email;

    public ValidationEmailRequest(String email) {
        this.email = email;
    }

    public static ValidationEmailRequest of(String email) {
        return new ValidationEmailRequest(email);
    }

}
