package me.kevinntech.onlineshop.user;

import lombok.*;
import javax.persistence.*;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    name = "USERS",
    uniqueConstraints = {
            @UniqueConstraint(name = "UQ__USERS__EMAIL", columnNames = "EMAIL"),
            @UniqueConstraint(name = "UQ__USERS__NICKNAME", columnNames = "NICKNAME")
    }
)
@Entity
public class User {

    @Id @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    private String email;

    private String nickname;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserGrade userGrade;

    private String bio;

    private String profileImage;

    @Builder
    public User(String email, String nickname, String password, UserGrade userGrade, String bio, String profileImage) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.userGrade = userGrade;
        this.bio = bio;
        this.profileImage = profileImage;
    }

}
