package me.kevinntech.onlineshop.auth.controller;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.auth.service.AuthService;
import me.kevinntech.onlineshop.auth.dto.LoginUser;
import me.kevinntech.onlineshop.auth.dto.SignInRequest;
import me.kevinntech.onlineshop.auth.dto.SignUpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static me.kevinntech.onlineshop.auth.SessionConst.LOGIN_USER;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "auth/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid @ModelAttribute SignUpRequest signUpRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "auth/sign-up";
        }

        authService.signUp(signUpRequest.toDto());

        return "redirect:/";
    }

    @GetMapping("/sign-in")
    public String signInForm(Model model) {
        model.addAttribute("signInRequest", new SignInRequest());
        return "auth/sign-in";
    }

    @PostMapping("/sign-in")
    public String signIn(@Valid @ModelAttribute SignInRequest signInRequest, Model model, HttpSession httpSession) {
        LoginUser loginUser = authService.signIn(signInRequest.toDto());

        if (loginUser == null) {
            model.addAttribute("loginFail", "아이디 또는 패스워드가 맞지 않습니다.");
            return "auth/sign-in";
        }

        httpSession.setAttribute(LOGIN_USER, loginUser);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession httpSession) {
        if (!httpSession.isNew()) {
            httpSession.invalidate();
        }

        return "redirect:/";
    }
}
