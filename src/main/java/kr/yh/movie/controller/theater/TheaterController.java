package kr.yh.movie.controller.theater;

import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.Seat;
import kr.yh.movie.domain.Theater;
import kr.yh.movie.service.CinemaService;
import kr.yh.movie.service.SeatService;
import kr.yh.movie.service.TheaterService;
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

import static kr.yh.movie.domain.Theater.*;

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
                          Model model){
        cinemaService.findById(cinemaId).ifPresent(vo->model.addAttribute("cinema",vo));
        model.addAttribute("form", new TheaterForm());
        return "theaters/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute TheaterForm form,
                      Errors errors,
                      Model model,
                      RedirectAttributes rttr) {
        if(DomainValidator.validate(errors, model)){
            return "theaters/add";
        }

        Cinema cinema = cinemaService.findById(form.getCinemaId()).get();
        Theater theater = createTheater(form, cinema);
        Theater savedTheater = theaterService.save(theater);

        rttr.addFlashAttribute("savedTheater", savedTheater);
        return "redirect:/theaters/list";
    }

    @GetMapping("/view")
    public String view(@ModelAttribute("cinemaId") Long cinemaId,
                       Long theaterId,
                       @ModelAttribute("pageVO") PageVO pageVO,
                       Model model){
        theaterService.findById(theaterId)
                      .ifPresent(vo->model.addAttribute("vo", vo));
        return "theaters/view";
    }

    @GetMapping("/modify")
    public String modifyForm(@ModelAttribute("cinemaId") Long cinemaId,
                             Long theaterId,
                             Model model){
        theaterService.findById(theaterId)
                      .ifPresent(vo->model.addAttribute("form", new TheaterForm(vo)));
        return "theaters/modify";
    }

    @PostMapping("/modify")
    public String modify(@Valid TheaterForm form,
                         Errors errors,
                         Model model,
                         RedirectAttributes rttr){
        if(DomainValidator.validate(errors, model)){
            return "theaters/modify";
        }

        Cinema cinema = cinemaService.findById(form.getCinemaId()).get();
        theaterService.findById(form.getId()).ifPresent(origin->{
            origin.changeInfo(form, cinema);
            Theater modifiedTheater = theaterService.save(origin);
            rttr.addFlashAttribute("modifiedTheater", modifiedTheater);
            rttr.addFlashAttribute("msg", "success");
            rttr.addAttribute("theaterId", origin.getId());
        });
        return "redirect:/theaters/view";
    }

    @PostMapping("/delete")
    public String delete(TheaterForm form,
                         RedirectAttributes rttr){
        theaterService.deleteById(form.getId());
        rttr.addFlashAttribute("msg", "success");
        return "redirect:/theaters/list";
    }

    @PostMapping("/deletes")
    public String deletes(@RequestParam(value = "checks") List<Long> theaterIds,
                          RedirectAttributes rttr){
        theaterService.deleteAllById(theaterIds);
        rttr.addFlashAttribute("msg", "success");
        return "redirect:/theaters/list";
    }
}
