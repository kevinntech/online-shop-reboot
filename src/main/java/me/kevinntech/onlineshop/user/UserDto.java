package me.kevinntech.onlineshop.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {

    private Long id;

    private String email;

    private String nickname;

    private String password;

    private UserGrade grade;

    private String bio;

    private String profileImage;

    @Builder
    public UserDto(String email, String nickname, String password, UserGrade grade, String bio, String profileImage) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.grade = grade;
        this.bio = bio;
        this.profileImage = profileImage;
    }

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .password(password)
                .userGrade(grade)
                .bio(bio)
                .profileImage(profileImage)
                .build();
    }

}
