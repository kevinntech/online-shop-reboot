package me.kevinntech.onlineshop.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.domain.auth.dto.ValidationEmailRequest;
import me.kevinntech.onlineshop.domain.auth.dto.ValidationNicknameRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AuthApiController {

    /*
     * 닉네임 중복 확인
     * */
    @PostMapping("/api/v1/auth/validate-nickname")
    public ResponseEntity validateNickname(@Valid @RequestBody ValidationNicknameRequest validationNicknameRequest){
        return ResponseEntity.ok().build();
    }

    /*
     * 이메일 중복 확인
     * */
    @PostMapping("/api/v1/auth/validate-email")
    public ResponseEntity validateEmail(@Valid @RequestBody ValidationEmailRequest validationEmailRequest){
        return ResponseEntity.ok().build();
    }

}
