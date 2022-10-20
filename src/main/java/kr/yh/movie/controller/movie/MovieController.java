package kr.yh.movie.controller.movie;

import kr.yh.movie.domain.Movie;
import kr.yh.movie.service.MovieService;
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
@RequestMapping("/movies")
@RequiredArgsConstructor
@Log
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/list")
    public String list(@ModelAttribute("pageVO")PageVO pageVO,
                       Model model){
        Pageable page = pageVO.makePageable(0, "id");
        Page<Movie> result = movieService.findAll(movieService.makePredicates(pageVO.getType(), pageVO.getKeyword()), page);
        model.addAttribute("result", new PageMarker<>(result));
        return "movies/list";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("form", new MovieForm());
        return "movies/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute MovieForm form,
                      Errors errors,
                      Model model,
                      RedirectAttributes rttr) {
        if(DomainValidator.validate(errors, model)){
            return "movies/add";
        }

        Movie movie = Movie.createMovie(form);
        movieService.save(movie);
        rttr.addFlashAttribute("movie", movie);
        return "redirect:/movies/list";
    }

    @GetMapping("/view")
    public String view(Long movieId,
                       @ModelAttribute("pageVO") PageVO pageVO,
                       Model model){
        movieService.findById(movieId)
                    .ifPresent(vo->model.addAttribute("vo", vo));
        return "movies/view";
    }

    @GetMapping("/modify")
    public String modifyForm(Long movieId,
                             Model model){
        movieService.findById(movieId)
                    .ifPresent(vo->model.addAttribute("form", new MovieForm(vo)));
        return "movies/modify";
    }

    @PostMapping("/modify")
    public String modify(@Valid MovieForm form,
                         Errors errors,
                         Model model,
                         RedirectAttributes rttr){
        if(DomainValidator.validate(errors, model)){
            return "movies/modify";
        }
        movieService.findById(form.getId()).ifPresent(origin->{
            origin.changeInfo(form);
            Movie modifiedMovie = movieService.save(origin);
            rttr.addFlashAttribute("modifiedMovie", modifiedMovie);
            rttr.addFlashAttribute("msg", "success");
            rttr.addAttribute("id", origin.getId());
        });
        return "redirect:/movies/view";
    }

    @PostMapping("/delete")
    public String delete(Long id, PageVO pageVO, RedirectAttributes rttr){
        log.info("DELETE ID : " + id);

        movieService.deleteById(id);

        rttr.addFlashAttribute("msg", "success");

        // 페이징과 검색했던 결과로 이동하는 경우
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);

        return "redirect:/movies/list";
    }

    @PostMapping("/deletes")
    public String deletes(@RequestParam(value = "checks") List<Long> ids, PageVO pageVO, RedirectAttributes rttr){
        log.info("DELETE IDS : " + ids);

        movieService.deleteAllById(ids);

        rttr.addFlashAttribute("msg", "success");

        // 페이징과 검색했던 결과로 이동하는 경우
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);

        return "redirect:/movies/list";
    }

}
