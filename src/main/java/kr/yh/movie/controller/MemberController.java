package kr.yh.movie.controller;

import kr.yh.movie.domain.member.Member;
import kr.yh.movie.service.MemberService;
import kr.yh.movie.validator.CheckEmailValidator;
import kr.yh.movie.validator.CheckPasswordEqualValidator;
import kr.yh.movie.validator.CheckPhoneValidator;
import kr.yh.movie.validator.CheckUserIdValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Log
public class MemberController {

    private final MemberService memberService;
    private final CheckUserIdValidator checkUserIdValidator;
    private final CheckPhoneValidator checkPhoneValidator;
    private final CheckEmailValidator checkEmailValidator;
    private final CheckPasswordEqualValidator checkPasswordEqualValidator;

    // 커스텀 유효성 검증을 위해 추가
    @InitBinder
    public void validatorBinder(WebDataBinder binder){
        binder.addValidators(checkUserIdValidator);
        binder.addValidators(checkPhoneValidator);
        binder.addValidators(checkEmailValidator);
        binder.addValidators(checkPasswordEqualValidator);
    }

    @GetMapping("/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute MemberForm memberForm, Errors errors, Model model){
        if(errors.hasErrors()){
            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for(String key : validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key));
            }
            // 회원가입 페이지로 다시 리턴
            return "members/createMemberForm";
        }

        Member member = Member.createMember(memberForm);
        memberService.signUp(member);
        return "redirect:/";
    }

    @GetMapping("/list")
    public String list(Model model){
        log.info("member list");
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
