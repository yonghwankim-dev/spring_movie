package kr.yh.movie.controller;

import kr.yh.movie.domain.Movie;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.service.MovieService;
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
@RequestMapping("/movies")
@RequiredArgsConstructor
@Log
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/list")
    public String list(@ModelAttribute("pageVO")PageVO pageVO, Model model){
        log.info("movie list");
        Pageable page = pageVO.makePageable(0, "id");
        Page<Movie> result = movieService.findAll(movieService.makePredicates(pageVO.getType(), pageVO.getKeyword()), page);
        model.addAttribute("result", new PageMarker<Movie>(result));
        return "movies/list";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute("pageVO")PageVO pageVO, Model model){
        log.info("movie add get");
        model.addAttribute("form", new MovieForm());
        return "movies/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute MovieForm movieForm, Errors errors, Model model) {
        log.info("movie add post" + movieForm);
        if(errors.hasErrors()){
            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = movieService.validateHandling(errors);
            for(String key : validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key));
            }
            // 회원가입 페이지로 다시 리턴
            return "movies/add";
        }

        Movie movie = Movie.createMovie(movieForm);
        movieService.save(movie);
        return "redirect:/movies/list";
    }

    @GetMapping("/view")
    public String view(Long id, @ModelAttribute("pageVO") PageVO pageVO, Model model){
        log.info("Movie Id : " + id);

        movieService.findById(id).ifPresent(vo->model.addAttribute("vo", vo));
        return "movies/view";
    }

    @GetMapping("/modify")
    public String modifyForm(Long id, @ModelAttribute("pageVO") PageVO pageVO, Model model){
        log.info("MODIFY FORM ID: " + id);

        movieService.findById(id).ifPresent(vo->model.addAttribute("form", new MovieForm(vo)));
        return "movies/modify";
    }

    @PostMapping("/modify")
    public String modify(MovieForm form, PageVO pageVO, RedirectAttributes rttr){
        log.info("Modify movie form : " + form);

        movieService.findById(form.getId()).ifPresent(origin->{
            origin.changeInfo(form);
            log.info("after origin.changeInfo : " + origin);
            movieService.save(origin);
            rttr.addFlashAttribute("msg", "success");
            rttr.addAttribute("id", origin.getId());
        });

        // 페이징과 검색했던 결과로 이동하는 경우
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);

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
