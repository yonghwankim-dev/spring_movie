package kr.yh.movie.controller;

import kr.yh.movie.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;

    @GetMapping
    public String createLoginForm(Model model,
          @RequestParam(value = "error", required = false) @ModelAttribute String error,
          @RequestParam(value = "exception", required = false) @ModelAttribute String exception){
        return "login/createLoginForm";
    }
}
