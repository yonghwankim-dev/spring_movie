package kr.yh.movie.controller.login;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
@Log
public class LoginController {
    @GetMapping
    public String loginForm(@RequestParam(value = "error", required = false, defaultValue = "")
                            @ModelAttribute String error,
                            @RequestParam(value = "exception", required = false, defaultValue = "")
                            @ModelAttribute String exception){return "login/login";}
}
