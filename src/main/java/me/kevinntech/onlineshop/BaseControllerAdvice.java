package me.kevinntech.onlineshop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@ControllerAdvice
public class BaseControllerAdvice {

    @ModelAttribute
    public void currentLoginUser(Model model) {
        model.addAttribute("loginUser", null);
    }

}
