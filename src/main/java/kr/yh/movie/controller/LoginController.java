package kr.yh.movie.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    @GetMapping
    public String createLoginForm(Model model,
          @RequestParam(value = "error", required = false) @ModelAttribute String error,
          @RequestParam(value = "exception", required = false) @ModelAttribute String exception){
        return "login/createLoginForm";
    }

    @RequestMapping("/logout")
    public void logout(){
        log.info("logout");
    }
}
