package me.kevinntech.onlineshop.domain.auth.controller;

import me.kevinntech.onlineshop.LoginSessionFactory;
import me.kevinntech.onlineshop.domain.auth.controller.AuthController;
import me.kevinntech.onlineshop.domain.auth.dto.LoginUser;
import me.kevinntech.onlineshop.domain.auth.service.AuthService;
import me.kevinntech.onlineshop.domain.user.UserDto;
import me.kevinntech.onlineshop.domain.user.UserGrade;
import me.kevinntech.onlineshop.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - Auth (쿠키, 세션)")
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired MockMvc mockMvc;

    @MockBean AuthService authService;
    @MockBean UserRepository userRepository;
    @MockBean LoginSessionFactory loginSessionFactory;

    MockHttpSession session;

    @BeforeEach
    void beforeEach() {
        LoginUser loginUser = LoginSessionFactory.createLoginUser("kevin@email.com", "kevin", UserGrade.USER);

        MockHttpSession willReturnSession = new MockHttpSession();
        willReturnSession.setAttribute("loginUser", loginUser);

        given(loginSessionFactory.createUserLoginSession(loginUser)).willReturn(willReturnSession);
        session = loginSessionFactory.createUserLoginSession(loginUser);
    }

    @AfterEach
    void afterEach() {
        session.clearAttributes();
        session = null;
    }

    @DisplayName("[View][GET] 회원가입 폼 페이지 조회")
    @Test
    void signUpForm() throws Exception {
        // Given

        // When & Then
        mockMvc.perform(get("/sign-up"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("auth/sign-up"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("signUpRequest"));
    }

    @DisplayName("[View][POST] 회원가입 처리 - 성공")
    @Test
    void signUp_success() throws Exception {
        // Given
        given(authService.signUp(any())).willReturn(true);
        given(userRepository.existsByNickname(any())).willReturn(false);
        given(userRepository.existsByEmail(any())).willReturn(false);

        // When & Then
        mockMvc.perform(
                post("/sign-up")
                        .param("nickname", "kevin")
                        .param("email", "kevin@email.com")
                        .param("password", "12345678")
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        then(authService).should().signUp(any());
        then(userRepository).should().existsByNickname(any());
        then(userRepository).should().existsByEmail(any());
    }

    @DisplayName("[View][POST] 회원가입 처리 - 잘못된 요청 파라미터 전달로 인한 실패")
    void signUp_fail() throws Exception {
        // Given

        // When & Then
        mockMvc.perform(
                        post("/sign-up")
                                .param("email", "kevin")
                                .param("password", "12345")
                                .param("nickname", "*kevin*")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("auth/sign-up"))
                .andExpect(model().hasErrors());

        then(authService).shouldHaveNoInteractions();
    }

    @DisplayName("[View][GET] 로그인 폼 페이지 조회")
    @Test
    void signInForm() throws Exception {
        // Given

        // When & Then
        mockMvc.perform(get("/sign-in"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("auth/sign-in"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("signInRequest"));
    }

    @DisplayName("[View][POST] 로그인 처리 - 성공")
    @Test
    void signIn_success() throws Exception {
        // Given
        String email = "kevin@email.com";
        String password = "12345678";
        UserDto userDto = UserDto.builder()
                .id(1L)
                .nickname("kevin")
                .email(email)
                .password(password)
                .userGrade(UserGrade.USER)
                .build();
        LoginUser loginUser = LoginUser.of(userDto);

        given(authService.signIn(any())).willReturn(loginUser);

        // When & Then
        mockMvc.perform(
                        post("/sign-in")
                                .param("username", email)
                                .param("password", password)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(redirectedUrl("/"));

        then(authService).should().signIn(any());
    }

    @DisplayName("[View][POST] 로그인 처리 - 실패")
    @Test
    void signIn_fail() throws Exception {
        // Given
        String email = "kevin@email.com";
        String password = "12345678";
        given(authService.signIn(any())).willReturn(null);

        // When & Then
        mockMvc.perform(
                        post("/sign-in")
                                .param("username", email)
                                .param("password", password)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("auth/sign-in"))
                .andExpect(model().attributeExists("loginFail"));

        then(authService).should().signIn(any());
    }

    @DisplayName("[View][POST] 로그아웃 처리")
    @Test
    void logout() throws Exception {
        // Given

        // When & Then
        mockMvc.perform(
                        post("/logout")
                                .session(session)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(redirectedUrl("/"));
    }

}