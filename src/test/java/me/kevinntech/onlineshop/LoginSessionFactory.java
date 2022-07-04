package me.kevinntech.onlineshop;

import me.kevinntech.onlineshop.domain.auth.dto.LoginUser;
import me.kevinntech.onlineshop.domain.auth.dto.SignInRequest;
import me.kevinntech.onlineshop.domain.auth.service.AuthService;
import me.kevinntech.onlineshop.domain.user.UserDto;
import me.kevinntech.onlineshop.domain.user.UserGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.stereotype.Component;

@Component
public class LoginSessionFactory {
    @Autowired AuthService authService;

    /*
     * 로그인 정보가 저장된 세션을 반환한다.
     * */
    public MockHttpSession createUserLoginSession(LoginUser loginUser) {
        // 회원 가입
        UserDto userDto = LoginSessionFactory.createUserDto(loginUser.getEmail(), loginUser.getNickname(), loginUser.getGrade());
        authService.signUp(userDto);

        // 회원 생성
        SignInRequest signInRequest = createLoginRequest(userDto);

        // 로그인
        authService.signIn(signInRequest.toDto());

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loginUser", loginUser);

        return session;
    }

    private SignInRequest createLoginRequest(UserDto userDto) {
        return SignInRequest.builder()
                .username(userDto.getNickname())
                .password(userDto.getPassword())
                .build();
    }

    public static LoginUser createLoginUser(String email, String nickname, UserGrade userGrade) {
        return LoginUser.builder()
                .email(email)
                .nickname(nickname)
                .grade(userGrade)
                .build();
    }

    public static UserDto createUserDto(String email, String nickname, UserGrade userGrade) {
        return UserDto.builder()
                .email(email)
                .nickname(nickname)
                .password("12345678")
                .userGrade(userGrade)
                .build();
    }
}
