package kr.yh.movie.controller;

import kr.yh.movie.domain.Address;
import kr.yh.movie.domain.Member;
import kr.yh.movie.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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
    public String create(@Valid MemberForm memberForm, BindingResult result){
        if(result.hasErrors()){
            return "members/createMemberForm";
        }
        Member member = Member.createMember(memberForm);
        memberService.signUp(member);
        return "redirect:/";
    }
}
