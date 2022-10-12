package kr.yh.movie.controller;

import kr.yh.movie.domain.ScreenSeat;
import kr.yh.movie.service.ScreenSeatService;
import kr.yh.movie.util.RedirectAttributeUtil;
import kr.yh.movie.vo.PageMarker;
import kr.yh.movie.vo.PageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/screenSeats")
@RequiredArgsConstructor
@Log
public class ScreenSeatController {
    private final ScreenSeatService service;
    
    @GetMapping("/list")
    public String list(Long screenId,
                       @ModelAttribute("pageVO") PageVO pageVO,
                       Model model){
        Pageable page = pageVO.makePageable(0, "id");
        Page<ScreenSeat> result = service.findAll(service.makePredicates(pageVO.getType(), pageVO.getKeyword(), screenId), page);
        model.addAttribute("result", new PageMarker<ScreenSeat>(result));
        return "screenSeats/list";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute("pageVO")PageVO pageVO, Model model){
        model.addAttribute("form", new ScreenSeatForm());
        return "screenSeats/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute ScreenSeatForm screenSeatForm, Errors errors, Model model) {
        if(errors.hasErrors()){
            Map<String, String> validatorResult = service.validateHandling(errors);
            for(String key : validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key));
            }
            return "screenSeats/add";
        }

        ScreenSeat screenSeat = ScreenSeat.createScreenSeat(screenSeatForm);
        service.save(screenSeat);
        return "redirect:/screenSeats/list";
    }

    @GetMapping("/view")
    public String view(Long id, @ModelAttribute("pageVO") PageVO pageVO, Model model){
        service.findById(id).ifPresent(vo->model.addAttribute("vo", vo));
        return "screenSeats/view";
    }

    @GetMapping("/modify")
    public String modifyForm(Long id, @ModelAttribute("pageVO") PageVO pageVO, Model model){
        service.findById(id).ifPresent(vo->model.addAttribute("form", new ScreenSeatForm(vo)));
        return "screenSeats/modify";
    }

    @PostMapping("/modify")
    public String modify(ScreenSeatForm form, PageVO pageVO, RedirectAttributes rttr){
        service.findById(form.getId()).ifPresent(origin->{
            origin.changeInfo(form);
            service.save(origin);
            rttr.addFlashAttribute("msg", "success");
            rttr.addAttribute("id", origin.getId());
        });
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);
        return "redirect:/screenSeats/view";
    }

    @PostMapping("/delete")
    public String delete(Long id, PageVO pageVO, RedirectAttributes rttr){
        service.deleteById(id);
        rttr.addFlashAttribute("msg", "success");
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);
        return "redirect:/screenSeats/list";
    }

    @PostMapping("/deletes")
    public String deletes(@RequestParam(value = "checks") List<Long> ids, PageVO pageVO, RedirectAttributes rttr){
        service.deleteAllById(ids);
        rttr.addFlashAttribute("msg", "success");
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);
        return "redirect:/screenSeats/list";
    }
}
