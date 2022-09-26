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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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

    @GetMapping("/new")
    public String createForm(){
        return null;
    }

    @PostMapping("/new")
    public String create(){
        return null;
    }

    @GetMapping("/view")
    public String view(){
        return null;
    }

    @GetMapping("/modify")
    public String modifyForm(){
        return null;
    }

    @PostMapping("/modify")
    public String modify(){
        return null;
    }

    @PostMapping("/delete")
    public String delete(){
        return null;
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
