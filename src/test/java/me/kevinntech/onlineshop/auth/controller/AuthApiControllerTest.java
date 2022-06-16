package me.kevinntech.onlineshop.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.kevinntech.onlineshop.auth.dto.ValidationEmailRequest;
import me.kevinntech.onlineshop.auth.dto.ValidationNicknameRequest;
import me.kevinntech.onlineshop.auth.validator.UniqueEmailValidator;
import me.kevinntech.onlineshop.auth.validator.UniqueNicknameValidator;
import me.kevinntech.onlineshop.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("API 컨트롤러 - Auth")
@WebMvcTest(AuthApiController.class)
class AuthApiControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean UserRepository userRepository;
    @MockBean UniqueNicknameValidator uniqueNicknameValidator;
    @MockBean UniqueEmailValidator uniqueEmailValidator;

    @DisplayName("[API][POST] 닉네임 중복 검증 - 성공")
    @Test
    void validateNickname_success() throws Exception {
        // Given
        String nickname = "admin";
        ValidationNicknameRequest validationNicknameRequest = ValidationNicknameRequest.of(nickname);
        String jsonString = objectMapper.writeValueAsString(validationNicknameRequest);

        given(uniqueNicknameValidator.isValid(eq(nickname), any())).willReturn(true);
        given(userRepository.existsByNickname(eq(nickname))).willReturn(false);

        // When & Then
        mockMvc.perform(
                post("/api/v1/auth/validate-nickname")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
        )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[API][POST] 닉네임 중복 검증 - 이미 존재하는 닉네임으로 인한 실패")
    @Test
    void validateNickname_fail() throws Exception {
        // Given
        String nickname = "admin";
        ValidationNicknameRequest validationNicknameRequest = ValidationNicknameRequest.of(nickname);
        String jsonString = objectMapper.writeValueAsString(validationNicknameRequest);

        given(uniqueNicknameValidator.isValid(eq(nickname), any())).willReturn(false);
        given(userRepository.existsByNickname(eq(nickname))).willReturn(true);

        // When & Then
        mockMvc.perform(
                        post("/api/v1/auth/validate-nickname")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonString)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @DisplayName("[API][POST] 이메일 중복 검증 - 성공")
    @Test
    void validateEmail_success() throws Exception {
        // Given
        String email = "admin@test.com";
        ValidationEmailRequest validationEmailRequest = ValidationEmailRequest.of(email);
        String jsonString = objectMapper.writeValueAsString(validationEmailRequest);

        given(uniqueEmailValidator.isValid(eq(email), any())).willReturn(true);
        given(userRepository.existsByEmail(eq(email))).willReturn(false);

        // When & Then
        mockMvc.perform(
                        post("/api/v1/auth/validate-email")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonString)
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @DisplayName("[API][POST] 닉네임 중복 검증 - 이미 존재하는 닉네임으로 인한 실패")
    @Test
    void validateEmail_fail() throws Exception {
        // Given
        String email = "admin@test.com";
        ValidationEmailRequest validationEmailRequest = ValidationEmailRequest.of(email);
        String jsonString = objectMapper.writeValueAsString(validationEmailRequest);

        given(uniqueEmailValidator.isValid(eq(email), any())).willReturn(false);
        given(userRepository.existsByEmail(eq(email))).willReturn(true);

        // When & Then
        mockMvc.perform(
                        post("/api/v1/auth/validate-email")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonString)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

}