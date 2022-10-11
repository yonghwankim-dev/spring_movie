package kr.yh.movie.controller;

import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.Seat;
import kr.yh.movie.domain.Theater;
import kr.yh.movie.service.CinemaService;
import kr.yh.movie.service.SeatService;
import kr.yh.movie.service.TheaterService;
import kr.yh.movie.util.RedirectAttributeUtil;
import kr.yh.movie.util.SeatUtil;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/theaters")
@SessionAttributes("cinemaId")
@RequiredArgsConstructor
@Log
public class TheaterController {
    private final TheaterService theaterService;
    private final SeatService    seatService;
    private final CinemaService  cinemaService;

    @GetMapping("/list")
    public String list(@ModelAttribute("cinemaId") Long cinemaId,
                       @ModelAttribute("pageVO") PageVO pageVO,
                       Model model){
        Pageable page = pageVO.makePageable(0, "id");
        Page<Theater> result = theaterService.findAll(theaterService.makePredicates(pageVO.getType(), pageVO.getKeyword(), cinemaId), page);
        model.addAttribute("result", new PageMarker<>(result));
        return "theaters/list";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute("cinemaId") Long cinemaId,
                          @ModelAttribute("pageVO")PageVO pageVO,
                          Model model){
        cinemaService.findById(cinemaId).ifPresent(vo->model.addAttribute("cinema",vo));
        model.addAttribute("form", new TheaterForm());
        return "theaters/add";
    }

    @PostMapping("/add")
    public String add(Long cinemaId,
                      @Valid @ModelAttribute TheaterForm theaterForm,
                      Errors errors,
                      Model model,
                      RedirectAttributes rttr) {
        if(errors.hasErrors()){
            Map<String, String> validatorResult = theaterService.validateHandling(errors);
            for(String key : validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key));
            }
            return "theaters/add";
        }
        cinemaService.findById(cinemaId).ifPresent(vo->theaterForm.setCinema(vo));
        Theater theater = Theater.createTheater(theaterForm);
        theaterService.save(theater);

        rttr.addAttribute("cinemaId", cinemaId);
        return "redirect:/theaters/list";
    }

    @GetMapping("/view")
    public String view(@ModelAttribute("cinemaId")Long cinemaId,
                       Long id,
                       @ModelAttribute("pageVO") PageVO pageVO,
                       Model model){
        Pageable page = pageVO.makePageable(0, "id");
        Page<Seat> result = seatService.findAll(seatService.makePredicates(pageVO.getType(), pageVO.getKeyword(), id), page);

        model.addAttribute("result", new PageMarker<>(result));
        theaterService.findById(id).ifPresent(vo->model.addAttribute("vo", vo));
        return "theaters/view";
    }



    @GetMapping("/modify")
    public String modifyForm(@ModelAttribute("cinemaId") Long cinemaId,
                             Long id,
                             @ModelAttribute("pageVO") PageVO pageVO,
                             Model model){
        theaterService.findById(id).ifPresent(vo->model.addAttribute("form", new TheaterForm(vo)));
        return "theaters/modify";
    }

    @PostMapping("/modify")
    public String modify(Long cinemaId,
                         TheaterForm form,
                         PageVO pageVO,
                         RedirectAttributes rttr){
        cinemaService.findById(cinemaId).ifPresent(vo->form.setCinema(vo));
        theaterService.findById(form.getId()).ifPresent(origin->{
            origin.changeInfo(form);
            theaterService.save(origin);
            rttr.addFlashAttribute("msg", "success");
            rttr.addAttribute("id", origin.getId());
        });

        rttr.addAttribute("cinemaId", cinemaId);
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);

        return "redirect:/theaters/view";
    }

    @PostMapping("/delete")
    public String delete(Long cinemaId,
                         Long id,
                         PageVO pageVO,
                         RedirectAttributes rttr){
        log.info("DELETE ID : " + id);

        theaterService.deleteById(id);

        rttr.addFlashAttribute("msg", "success");

        // 페이징과 검색했던 결과로 이동하는 경우
        rttr.addAttribute("cinemaId", cinemaId);
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);

        return "redirect:/theaters/list";
    }

    @PostMapping("/deletes")
    public String deletes(Long cinemaId, @RequestParam(value = "checks") List<Long> ids, PageVO pageVO, RedirectAttributes rttr){
        log.info("DELETE IDS : " + ids);
        log.info("cinemaId   : " + cinemaId);

        theaterService.deleteAllById(ids);

        rttr.addFlashAttribute("msg", "success");

        // 페이징과 검색했던 결과로 이동하는 경우
        rttr.addAttribute("cinemaId", cinemaId);
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);

        return "redirect:/theaters/list";
    }
}
