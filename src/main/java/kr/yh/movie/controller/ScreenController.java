package kr.yh.movie.controller;

import kr.yh.movie.domain.Movie;
import kr.yh.movie.domain.Screen;
import kr.yh.movie.domain.Theater;
import kr.yh.movie.service.MovieService;
import kr.yh.movie.service.ScreenService;
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
@RequestMapping("/screens")
@RequiredArgsConstructor
@Log
public class ScreenController {
    private final ScreenService  screenService;
    private final MovieService   movieService;
    private final TheaterService theaterService;

    @GetMapping("/list")
    public String list(@ModelAttribute("pageVO") PageVO pageVO, Model model){
        Pageable page = pageVO.makePageable(0, "id");
        Page<Screen> result = screenService.findAll(screenService.makePredicates(pageVO.getType(), pageVO.getKeyword()), page);
        model.addAttribute("result", new PageMarker<Screen>(result));
        return "screens/list";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute("pageVO")PageVO pageVO, Model model){
        model.addAttribute("form", new ScreenForm());
        return "screens/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute ScreenForm screenForm, Errors errors, Model model) {
        if(errors.hasErrors()){
            Map<String, String> validatorResult = screenService.validateHandling(errors);
            for(String key : validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key));
            }
            return "screens/add";
        }

        Screen screen = Screen.createScreen(screenForm);
        screenService.save(screen);
        return "redirect:/screens/list";
    }

    @GetMapping("/view")
    public String view(Long id, @ModelAttribute("pageVO") PageVO pageVO, Model model){
        screenService.findById(id).ifPresent(vo->model.addAttribute("vo", vo));
        return "screens/view";
    }

    @GetMapping("/modify")
    public String modifyForm(Long id,
                             @ModelAttribute("cinemaId") Long cinemaId,
                             @ModelAttribute("pageVO") PageVO pageVO,
                             Model model){
        List<Movie> movies = (List<Movie>) movieService.findAll();
        List<Theater> theaters = (List<Theater>) theaterService.findAllByCinemaId(cinemaId);

        screenService.findById(id).ifPresent(vo->model.addAttribute("form", new ScreenForm(vo)));
        model.addAttribute("movies", movies);
        model.addAttribute("theaters", theaters);
        return "screens/modify";
    }

    @PostMapping("/modify")
    public String modify(ScreenForm form, PageVO pageVO, RedirectAttributes rttr){
        screenService.findById(form.getId()).ifPresent(origin->{
            origin.changeInfo(form);
            screenService.save(origin);
            rttr.addFlashAttribute("msg", "success");
            rttr.addAttribute("id", origin.getId());
        });
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);
        return "redirect:/screens/view";
    }

    @PostMapping("/delete")
    public String delete(Long id, PageVO pageVO, RedirectAttributes rttr){
        screenService.deleteById(id);
        rttr.addFlashAttribute("msg", "success");
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);
        return "redirect:/screens/list";
    }

    @PostMapping("/deletes")
    public String deletes(@RequestParam(value = "checks") List<Long> ids, PageVO pageVO, RedirectAttributes rttr){
        screenService.deleteAllById(ids);
        rttr.addFlashAttribute("msg", "success");
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);
        return "redirect:/screens/list";
    }
}
