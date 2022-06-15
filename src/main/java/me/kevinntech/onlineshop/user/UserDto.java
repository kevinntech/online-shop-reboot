package me.kevinntech.onlineshop.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {

    private Long id;

    private String email;

    private String nickname;

    private String password;

    private UserGrade userGrade;

    private String bio;

    private String profileImage;

    @Builder
    public UserDto(Long id, String email, String nickname, String password, UserGrade userGrade, String bio, String profileImage) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.userGrade = userGrade;
        this.bio = bio;
        this.profileImage = profileImage;
    }

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .password(password)
                .userGrade(userGrade)
                .bio(bio)
                .profileImage(profileImage)
                .build();
    }

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .password(user.getPassword())
                .userGrade(user.getUserGrade())
                .bio(user.getBio())
                .profileImage(user.getProfileImage())
                .build();
    }
}
