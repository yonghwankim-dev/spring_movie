package kr.yh.movie.controller;

import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.Theater;
import kr.yh.movie.service.TheaterService;
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
@RequestMapping("/theaters")
@RequiredArgsConstructor
@Log
public class TheaterController {
    private final TheaterService theaterService;

    @GetMapping("/list")
    public String list(Long id, @ModelAttribute("pageVO") PageVO pageVO, Model model){
        log.info("theater list");
        Pageable page = pageVO.makePageable(0, "id");
        Page<Theater> result = theaterService.findAll(theaterService.makePredicates(pageVO.getType(), pageVO.getKeyword()), page);
        model.addAttribute("result", new PageMarker<Theater>(result));
        return "cinemas/theaters/list";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute("pageVO")PageVO pageVO, Model model){
        log.info("theater add get");
        model.addAttribute("form", new TheaterForm());
        return "cinemas/theaters/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute TheaterForm theaterForm, Errors errors, Model model) {
        log.info("theater add post" + theaterForm);
        if(errors.hasErrors()){
            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = theaterService.validateHandling(errors);
            for(String key : validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key));
            }
            return "cinemas/theaters/add";
        }

        Theater theater = Theater.createTheater(theaterForm);
        theaterService.save(theater);
        return "redirect:/cinemas/theaters/list";
    }

    @GetMapping("/view")
    public String view(Long id, @ModelAttribute("pageVO") PageVO pageVO, Model model){
        log.info("theater Id : " + id);

        theaterService.findById(id).ifPresent(vo->model.addAttribute("vo", vo));
        return "cinemas/theaters/view";
    }

    @GetMapping("/modify")
    public String modifyForm(Long id, @ModelAttribute("pageVO") PageVO pageVO, Model model){
        log.info("MODIFY FORM ID: " + id);

        theaterService.findById(id).ifPresent(vo->model.addAttribute("form", new TheaterForm(vo)));
        return "cinemas/theaters/modify";
    }

    @PostMapping("/modify")
    public String modify(TheaterForm form, PageVO pageVO, RedirectAttributes rttr){
        log.info("Modify theater form : " + form);

        theaterService.findById(form.getId()).ifPresent(origin->{
            origin.changeInfo(form);
            log.info("after origin.changeInfo : " + origin);
            theaterService.save(origin);
            rttr.addFlashAttribute("msg", "success");
            rttr.addAttribute("id", origin.getId());
        });

        // 페이징과 검색했던 결과로 이동하는 경우
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);

        return "redirect:/cinemas/theaters/view";
    }

    @PostMapping("/delete")
    public String delete(Long id, PageVO pageVO, RedirectAttributes rttr){
        log.info("DELETE ID : " + id);

        theaterService.deleteById(id);

        rttr.addFlashAttribute("msg", "success");

        // 페이징과 검색했던 결과로 이동하는 경우
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);

        return "redirect:/cinemas/theaters/list";
    }

    @PostMapping("/deletes")
    public String deletes(@RequestParam(value = "checks") List<Long> ids, PageVO pageVO, RedirectAttributes rttr){
        log.info("DELETE IDS : " + ids);

        theaterService.deleteAllById(ids);

        rttr.addFlashAttribute("msg", "success");

        // 페이징과 검색했던 결과로 이동하는 경우
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);

        return "redirect:/cinemas/theaters/list";
    }
}
