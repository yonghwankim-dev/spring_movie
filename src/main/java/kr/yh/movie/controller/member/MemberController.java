package kr.yh.movie.controller.member;

import kr.yh.movie.domain.member.Member;
import kr.yh.movie.service.MemberService;
import kr.yh.movie.validator.*;
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
@RequestMapping("/members")
@RequiredArgsConstructor
@Log
public class MemberController {

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

    @GetMapping("/list")
    public String list(PageVO pageVO, Model model){
        Pageable page = pageVO.makePageable(0, "id");
        Page<MemberDTO> result = memberService.findAll(memberService.makePredicates(pageVO.getType(), pageVO.getKeyword()), page)
                                              .map(MemberDTO::of);
        model.addAttribute("result", new PageMarker<>(result));
        model.addAttribute("pageVO", pageVO);
        return "members/list";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("form", MemberDTO.createMemberDTO());
        return "members/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("form") MemberDTO memberDTO,
                      Errors errors,
                      Model model,
                      RedirectAttributes rttr){
        if(DomainValidator.validate(errors, model)){
            log.info("birthday error : " + errors.getFieldError("birthday").getDefaultMessage());
            return "members/add";
        }

        Member savedMember = memberService.save(Member.of(memberDTO));
        MemberDTO savedMemberDto = MemberDTO.of(savedMember);
        rttr.addFlashAttribute("savedMember", savedMemberDto);
        return "redirect:/";
    }

    @GetMapping("/view")
    public String view(Long memberId,
                       @ModelAttribute("pageVO") PageVO pageVO,
                       Model model){
        memberService.findById(memberId)
                     .ifPresent(member->
                             model.addAttribute("member", MemberDTO.of(member)));
        return "members/view";
    }

    @GetMapping("/modify")
    public String modifyForm(Long memberId,
                             Model model){
        memberService.findById(memberId)
                     .ifPresent(member->
                             model.addAttribute("form", MemberDTO.of(member)));
        return "members/modify";
    }

    @PostMapping("/modify")
    public String modify(MemberDTO form,
                         RedirectAttributes rttr){
        memberService.findById(form.getId())
                     .ifPresent(origin->{
            origin.changeInfo(form);
            memberService.save(origin);
            rttr.addFlashAttribute("msg", "success");
            rttr.addAttribute("memberId", origin.getId());
        });
        return "redirect:/members/view";
    }

    @PostMapping("/delete")
    public String delete(MemberDTO form,
                         RedirectAttributes rttr){
        memberService.deleteById(form.getId());
        rttr.addFlashAttribute("msg", "success");
        return "redirect:/members/list";
    }

    @PostMapping("/deletes")
    public String deletes(@RequestParam(value = "checks") List<Long> memberIds,
                          RedirectAttributes rttr){
        memberService.deleteAllById(memberIds);
        rttr.addFlashAttribute("msg", "success");
        return "redirect:/members/list";
    }
}
