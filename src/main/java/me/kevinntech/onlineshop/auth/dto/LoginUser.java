package me.kevinntech.onlineshop.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import me.kevinntech.onlineshop.user.UserDto;
import me.kevinntech.onlineshop.user.UserGrade;

@Getter
@ToString(of = {"email", "nickname", "grade", "isAuthenticated"})
public class LoginUser {

    private String email;

    private String nickname;

    private UserGrade grade;

    private boolean isAuthenticated;

    @Builder
    private LoginUser(String email, String nickname, UserGrade grade) {
        this.email = email;
        this.nickname = nickname;
        this.grade = grade;
        this.isAuthenticated = email != null ? true : false;
    }

    public static LoginUser of(UserDto userDto) {
        return new LoginUser(
                userDto.getEmail(),
                userDto.getNickname(),
                userDto.getUserGrade());
    }
}