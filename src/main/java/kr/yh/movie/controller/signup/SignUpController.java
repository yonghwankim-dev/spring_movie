package kr.yh.movie.controller.signup;

import kr.yh.movie.controller.member.MemberDTO;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.service.MemberService;
import kr.yh.movie.validator.DomainValidator;
import kr.yh.movie.validator.MemberValidator;
import kr.yh.movie.vo.PageMarker;
import kr.yh.movie.vo.PageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
@Log
public class SignUpController {

    private final MemberService memberService;
    private final MemberValidator memberValidator;

    @InitBinder
    public void validatorBinder(WebDataBinder binder){
        binder.addValidators(memberValidator.getCheckUserIdValidator());
        binder.addValidators(memberValidator.getCheckPhoneValidator());
        binder.addValidators(memberValidator.getCheckEmailValidator());
        binder.addValidators(memberValidator.getCheckPasswordEqualValidator());
        binder.addValidators(memberValidator.getCheckBirthdayValidator());
    }

    @GetMapping
    public String addForm(Model model){
        model.addAttribute("form", MemberDTO.createMemberDTO());
        return "signup/signup";
    }

    @PostMapping
    public String add(@Valid @ModelAttribute("form") MemberDTO memberDTO,
                      Errors errors,
                      Model model,
                      RedirectAttributes rttr){
        if(DomainValidator.validate(errors, model)){
            return "signup/signup";
        }

        Member savedMember = memberService.save(Member.of(memberDTO));
        MemberDTO savedMemberDto = MemberDTO.of(savedMember);
        rttr.addFlashAttribute("savedMember", savedMemberDto);
        return "redirect:/";
    }
}
