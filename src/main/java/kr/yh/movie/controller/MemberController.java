package kr.yh.movie.controller;

import kr.yh.movie.domain.Address;
import kr.yh.movie.domain.Member;
import kr.yh.movie.service.MemberService;
import kr.yh.movie.validator.CheckEmailValidator;
import kr.yh.movie.validator.CheckPhoneValidator;
import kr.yh.movie.validator.CheckUserIdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
class MemberController {

    private final MemberService memberService;
    private final CheckUserIdValidator checkUserIdValidator;
    private final CheckPhoneValidator checkPhoneValidator;
    private final CheckEmailValidator checkEmailValidator;

    // 커스텀 유효성 검증을 위해 추가
    @InitBinder
    public void validatorBinder(WebDataBinder binder){
        binder.addValidators(checkUserIdValidator);
        binder.addValidators(checkPhoneValidator);
        binder.addValidators(checkEmailValidator);
    }

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, Errors errors, Model model){
        if(errors.hasErrors()){
            // 회원가입 실패시 입력 데이터 값을 유지
            model.addAttribute("memberForm", memberForm);

            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for(String key : validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key));
            }
            // 회원가입 페이지로 다시 리턴
            return "members/createMemberForm";
        }

        // 핸드폰번호, 이메일, 아이디 중복 여부 검사
        memberService.checkPhoneDuplication(memberForm);
        memberService.checkEmailDuplication(memberForm);
        memberService.checkUserIdDuplication(memberForm);

        Member member = Member.createMember(memberForm);
        memberService.signUp(member);
        return "redirect:/";
    }
}
