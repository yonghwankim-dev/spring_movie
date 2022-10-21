package kr.yh.movie.controller.screen;

import kr.yh.movie.domain.Movie;
import kr.yh.movie.domain.Screen;
import kr.yh.movie.domain.Theater;
import kr.yh.movie.service.MovieService;
import kr.yh.movie.service.ScreenService;
import kr.yh.movie.service.TheaterService;
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

import static kr.yh.movie.domain.Screen.*;
import static kr.yh.movie.util.RedirectAttributeUtil.*;

@Controller
@RequestMapping("/screens")
@SessionAttributes("cinemaId")
@RequiredArgsConstructor
@Log
public class ScreenController {
    private final ScreenService  screenService;
    private final MovieService   movieService;
    private final TheaterService theaterService;

    @GetMapping("/list")
    public String list(@ModelAttribute("pageVO") PageVO pageVO,
                       Model model){
        Pageable page = pageVO.makePageable(0, "id");
        Page<Screen> result = screenService.findAll(screenService.makePredicates(pageVO.getType(), pageVO.getKeyword()), page);
        model.addAttribute("result", new PageMarker<>(result));
        return "screens/list";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute("cinemaId") Long cinemaId,
                          Model model){
        List<Movie> movies = (List<Movie>) movieService.findAll();
        List<Theater> theaters = theaterService.findAllByCinemaId(cinemaId);

        model.addAttribute("form", new ScreenForm());
        model.addAttribute("movies", movies);
        model.addAttribute("theaters", theaters);
        return "screens/add";
    }

    @PostMapping("/add")
    public String add(@Valid ScreenForm form,
                      Errors errors,
                      Model model,
                      RedirectAttributes rttr) {
        if(DomainValidator.validate(errors, model)){
            return "screens/add";
        }

        Movie movie = movieService.findById(form.getMovieId()).get();
        Theater theater = theaterService.findById(form.getTheaterId()).get();
        Screen savedScreen = screenService.save(createScreen(form.getStartDateTime(),
                                                             form.getRound(),
                                                             movie,
                                                             theater));
        rttr.addFlashAttribute("savedScreen", savedScreen);
        return "redirect:/screens/list";
    }

    @GetMapping("/view")
    public String view(Long screenId,
                       @ModelAttribute("pageVO") PageVO pageVO,
                       Model model){
        screenService.findById(screenId).ifPresent(vo->model.addAttribute("vo", vo));
        return "screens/view";
    }

    @GetMapping("/modify")
    public String modifyForm(@ModelAttribute("cinemaId") Long cinemaId,
                             Long screenId,
                             Model model){
        List<Movie> movies = (List<Movie>) movieService.findAll();
        List<Theater> theaters = theaterService.findAllByCinemaId(cinemaId);

        screenService.findById(screenId).ifPresent(vo->model.addAttribute("form", new ScreenForm(vo)));
        model.addAttribute("movies", movies);
        model.addAttribute("theaters", theaters);
        return "screens/modify";
    }

    @PostMapping("/modify")
    public String modify(@Valid ScreenForm form,
                         Errors errors,
                         Model model,
                         RedirectAttributes rttr){
        if(DomainValidator.validate(errors, model)){
            return "screens/modify";
        }

        Movie movie = movieService.findById(form.getMovieId()).get();
        Theater theater = theaterService.findById(form.getTheaterId()).get();

        screenService.findById(form.getId()).ifPresent(origin->{
            origin.changeInfo(form, movie, theater);
            screenService.save(origin);
            rttr.addFlashAttribute("msg", "success");
            rttr.addAttribute("screenId", origin.getId());
        });

        return "redirect:/screens/view";
    }

    @PostMapping("/delete")
    public String delete(ScreenForm form,
                         RedirectAttributes rttr){
        screenService.deleteById(form.getId());
        rttr.addFlashAttribute("msg", "success");
        return "redirect:/screens/list";
    }

    @PostMapping("/deletes")
    public String deletes(@RequestParam(value = "checks") List<Long> screenIds,
                          RedirectAttributes rttr){
        screenService.deleteAllById(screenIds);
        rttr.addFlashAttribute("msg", "success");
        return "redirect:/screens/list";
    }
}
