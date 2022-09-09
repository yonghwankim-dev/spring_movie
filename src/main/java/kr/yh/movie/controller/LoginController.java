package kr.yh.movie.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    @GetMapping
    public String createLoginForm(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "login/createLoginForm";
    }

    @PostMapping
    public String login(){
        return "redirect:/";
    }
}
