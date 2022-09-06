package kr.yh.movie.controller;

import kr.yh.movie.domain.Address;
import kr.yh.movie.domain.Member;
import kr.yh.movie.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            // 회원가입 실패시 데이터 유지
            model.addAttribute("memberForm", memberForm);
            Map<String, String> validatorResult = memberService.validateHandling(bindingResult);
            for(String key : validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key));
            }
            return "members/createMemberForm";
        }
        Member member = Member.createMember(memberForm);
        memberService.signUp(member);
        return "redirect:/";
    }
}
