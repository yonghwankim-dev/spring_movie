package kr.yh.movie.controller;

import kr.yh.movie.domain.member.Member;
import kr.yh.movie.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final MemberService memberService;

    @GetMapping
    public String createLoginForm(Model model,
          @RequestParam(value = "error", required = false) @ModelAttribute String error,
          @RequestParam(value = "exception", required = false) @ModelAttribute String exception){
        log.info("GetMapping createLoginForm");
        return "login/createLoginForm";
    }

    @RequestMapping("/logout")
    public void logout(){
        log.info("logout");
    }
}
