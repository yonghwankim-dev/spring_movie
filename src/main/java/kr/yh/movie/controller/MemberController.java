package kr.yh.movie.controller;

import kr.yh.movie.domain.member.Member;
import kr.yh.movie.service.MemberService;
import kr.yh.movie.validator.CheckEmailValidator;
import kr.yh.movie.validator.CheckPasswordEqualValidator;
import kr.yh.movie.validator.CheckPhoneValidator;
import kr.yh.movie.validator.CheckUserIdValidator;
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
import java.util.Map;

@Controller
@RequestMapping("/members")
@SessionAttributes("pageVO")
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

    @GetMapping("/list")
    public String list(PageVO pageVO, Model model){
        Pageable page = pageVO.makePageable(0, "id");
        Page<Member> result = memberService.findAll(memberService.makePredicates(pageVO.getType(), pageVO.getKeyword()), page);
        model.addAttribute("result", new PageMarker<>(result));
        model.addAttribute("pageVO", pageVO);
        return "members/list";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute MemberForm memberForm, Errors errors, Model model){
        if(errors.hasErrors()){
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for(String key : validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key));
            }
            return "members/add";
        }

        Member member = Member.createMember(memberForm);
        memberService.signUp(member);
        return "redirect:/";
    }

    @GetMapping("/view")
    public String view(Long id, @ModelAttribute("pageVO") PageVO pageVO, Model model){
        memberService.findById(id).ifPresent(member->model.addAttribute("member", member));
        return "members/view";
    }

    @GetMapping("/modify")
    public String modifyForm(Long id, @ModelAttribute("pageVO") PageVO pageVO, Model model){
        memberService.findById(id).ifPresent(member->model.addAttribute("form", new MemberForm(member)));
        return "members/modify";
    }

    @PostMapping("/modify")
    public String modify(MemberForm form, RedirectAttributes rttr){
        memberService.findById(form.getId()).ifPresent(origin->{
            origin.changeInfo(form);
            memberService.save(origin);
            rttr.addFlashAttribute("msg", "success");
            rttr.addAttribute("id", origin.getId());
        });
        return "redirect:/members/view";
    }

    @PostMapping("/delete")
    public String delete(Long id, RedirectAttributes rttr){
        memberService.deleteById(id);

        rttr.addFlashAttribute("msg", "success");
        return "redirect:/members/list";
    }

    @PostMapping("/deletes")
    public String deletes(@RequestParam(value = "checks") List<Long> ids, RedirectAttributes rttr){
        memberService.deleteAllById(ids);

        rttr.addFlashAttribute("msg", "success");
        return "redirect:/members/list";
    }
}
