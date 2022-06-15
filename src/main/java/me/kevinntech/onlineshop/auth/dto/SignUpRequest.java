package me.kevinntech.onlineshop.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kevinntech.onlineshop.auth.annotation.UniqueEmail;
import me.kevinntech.onlineshop.auth.annotation.UniqueNickname;
import me.kevinntech.onlineshop.user.UserDto;
import me.kevinntech.onlineshop.user.UserGrade;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class SignUpRequest {

    @UniqueNickname
    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
    private String nickname;

    @Email
    @NotBlank
    private String email;

    @UniqueEmail
    @NotBlank
    @Length(min = 8, max = 50)
    private String password;

    public UserDto toDto() {
        return UserDto.builder()
                .nickname(nickname)
                .email(email)
                .password(password)
                .userGrade(UserGrade.USER)  // 기본 값은 USER로 설정한다.
                .build();
    }
}
