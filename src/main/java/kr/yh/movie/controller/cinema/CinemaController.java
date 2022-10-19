package kr.yh.movie.controller.cinema;

import kr.yh.movie.domain.Cinema;
import kr.yh.movie.service.CinemaService;
import kr.yh.movie.util.RedirectAttributeUtil;
import kr.yh.movie.validator.DomainValidator;
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
@RequestMapping("/cinemas")
@SessionAttributes("cinemaId")
@RequiredArgsConstructor
@Log
public class CinemaController {
    private final CinemaService cinemaService;

    @GetMapping("/list")
    public String list(@ModelAttribute("pageVO") PageVO pageVO,
                       Model model){
        Pageable page = pageVO.makePageable(0, "id");
        Page<Cinema> result = cinemaService.findAll(cinemaService.makePredicates(pageVO.getType(), pageVO.getKeyword()), page);
        model.addAttribute("result", new PageMarker<>(result));
        return "cinemas/list";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("form", new CinemaForm());
        return "cinemas/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute CinemaForm cinemaForm,
                      Errors errors,
                      Model model,
                      RedirectAttributes rttr) {
        if(DomainValidator.validate(errors, model)){
            return "cinemas/add";
        }

        Cinema cinema = Cinema.createCinema(cinemaForm);
        Cinema savedCinema = cinemaService.save(cinema);
        rttr.addFlashAttribute("savedCinema", savedCinema);
        return "redirect:/cinemas/list";
    }

    @GetMapping("/view")
    public String view(Long cinemaId,
                       @ModelAttribute("pageVO") PageVO pageVO,
                       Model model){
        cinemaService.findById(cinemaId)
                     .ifPresent(vo->model.addAttribute("vo", vo));
        return "cinemas/view";
    }

    @GetMapping("/modify")
    public String modifyForm(Long id, @ModelAttribute("pageVO") PageVO pageVO, Model model){
        log.info("MODIFY FORM ID: " + id);

        cinemaService.findById(id).ifPresent(vo->model.addAttribute("form", new CinemaForm(vo)));
        return "cinemas/modify";
    }

    @PostMapping("/modify")
    public String modify(CinemaForm form, PageVO pageVO, RedirectAttributes rttr){
        log.info("Modify cinema form : " + form);

        cinemaService.findById(form.getId()).ifPresent(origin->{
            origin.changeInfo(form);
            log.info("after origin.changeInfo : " + origin);
            cinemaService.save(origin);
            rttr.addFlashAttribute("msg", "success");
            rttr.addAttribute("id", origin.getId());
        });

        // 페이징과 검색했던 결과로 이동하는 경우
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);

        return "redirect:/cinemas/view";
    }

    @PostMapping("/delete")
    public String delete(Long id, PageVO pageVO, RedirectAttributes rttr){
        log.info("DELETE ID : " + id);

        cinemaService.deleteById(id);

        rttr.addFlashAttribute("msg", "success");

        // 페이징과 검색했던 결과로 이동하는 경우
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);

        return "redirect:/cinemas/list";
    }

    @PostMapping("/deletes")
    public String deletes(@RequestParam(value = "checks") List<Long> ids, PageVO pageVO, RedirectAttributes rttr){
        log.info("DELETE IDS : " + ids);

        cinemaService.deleteAllById(ids);

        rttr.addFlashAttribute("msg", "success");

        // 페이징과 검색했던 결과로 이동하는 경우
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);

        return "redirect:/cinemas/list";
    }

    @GetMapping("/home")
    public String home(Long cinemaId, Model model){
        cinemaService.findById(cinemaId).ifPresent(vo->model.addAttribute("vo", vo));
        model.addAttribute("cinemaId", cinemaId);
        return "cinemas/home";
    }
}
