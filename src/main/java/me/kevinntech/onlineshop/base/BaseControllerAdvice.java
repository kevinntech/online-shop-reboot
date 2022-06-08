package me.kevinntech.onlineshop.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@ControllerAdvice
public class BaseControllerAdvice {

    @ModelAttribute
    public void currentLoginUser(Model model) {
        model.addAttribute("loginUser", null);
    }

    // 스프링이 Setter가 아닌 Field에 직접 접근 하도록 한다.
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
    }
}
