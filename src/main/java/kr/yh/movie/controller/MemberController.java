package kr.yh.movie.controller;

import kr.yh.movie.domain.member.Member;
import kr.yh.movie.service.MemberService;
import kr.yh.movie.util.RedirectAttributeUtil;
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
import org.springframework.transaction.annotation.Transactional;
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
    public String list(@ModelAttribute("pageVO")PageVO pageVO, Model model){
        log.info("member list");
        Pageable page = pageVO.makePageable(0, "id");
        Page<Member> result = memberService.findAll(memberService.makePredicates(pageVO.getType(), pageVO.getKeyword()), page);
        model.addAttribute("result", new PageMarker<Member>(result));
        return "members/list";
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

    @GetMapping("/view")
    public String view(Long id, @ModelAttribute("pageVO") PageVO pageVO, Model model){
        log.info("Member Id : " + id);

        memberService.findById(id).ifPresent(member->model.addAttribute("member", member));
        return "members/view";
    }

    @GetMapping("/modify")
    public String modifyForm(Long id, @ModelAttribute("pageVO") PageVO pageVO, Model model){
        log.info("MODIFY ID: " + id);

        memberService.findById(id).ifPresent(member->model.addAttribute("form", new MemberForm(member)));
        return "members/modify";
    }

    @PostMapping("/modify")
    public String modify(MemberForm form, PageVO pageVO, RedirectAttributes rttr){
        log.info("Modify member form : " + form);

        memberService.findById(form.getId()).ifPresent(origin->{
            origin.changeInfo(form);
            log.info("after origin.changeInfo : " + origin);
            memberService.save(origin);
            rttr.addFlashAttribute("msg", "success");
            rttr.addAttribute("id", origin.getId());
        });

        // 페이징과 검색했던 결과로 이동하는 경우
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);

        return "redirect:/members/view";
    }

    @PostMapping("/delete")
    public String delete(Long id, PageVO pageVO, RedirectAttributes rttr){
        log.info("DELETE ID : " + id);

        memberService.deleteById(id);

        rttr.addFlashAttribute("msg", "success");

        // 페이징과 검색했던 결과로 이동하는 경우
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);

        return "redirect:/members/list";
    }

    @PostMapping("/deletes")
    public String deletes(@RequestParam(value = "checks") List<Long> ids, PageVO pageVO, RedirectAttributes rttr){
        log.info("DELETE IDS : " + ids);

        memberService.deleteAllById(ids);

        rttr.addFlashAttribute("msg", "success");

        // 페이징과 검색했던 결과로 이동하는 경우
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);

        return "redirect:/members/list";
    }

}
