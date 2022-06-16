package me.kevinntech.onlineshop.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kevinntech.onlineshop.auth.annotation.UniqueNickname;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ValidationNicknameRequest {

    @NotBlank
    @UniqueNickname
    private String nickname;

    public ValidationNicknameRequest(String nickname) {
        this.nickname = nickname;
    }

    public static ValidationNicknameRequest of(String nickname) {
        return new ValidationNicknameRequest(nickname);
    }

}
