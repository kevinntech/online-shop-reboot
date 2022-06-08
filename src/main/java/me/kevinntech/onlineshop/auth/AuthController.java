package me.kevinntech.onlineshop.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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

}
