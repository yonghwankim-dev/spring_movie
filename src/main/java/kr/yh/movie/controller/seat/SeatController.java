package kr.yh.movie.controller.seat;

import kr.yh.movie.domain.Seat;
import kr.yh.movie.domain.Theater;
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

import static kr.yh.movie.domain.Seat.*;

@Controller
@RequestMapping("/seats")
@RequiredArgsConstructor
@Log
public class SeatController {
    private final SeatService seatService;
    private final TheaterService theaterService;

    @GetMapping("/list")
    public String list(@ModelAttribute("theaterId") Long theaterId,
                       @ModelAttribute("pageVO") PageVO pageVO,
                       Model model){
        Pageable page = pageVO.makePageable(0, "id");
        Page<Seat> result = seatService.findAll(seatService.makePredicates(pageVO.getType(), pageVO.getKeyword(), theaterId), page);
        model.addAttribute("result", new PageMarker<>(result));
        return "seats/list";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute("theaterId") Long theaterId,
                          Model model){
        model.addAttribute("form", new SeatForm());
        return "seats/add";
    }

    @PostMapping("/add")
    public String add(@Valid SeatForm form,
                      Errors errors,
                      Model model,
                      RedirectAttributes rttr) {
        if(DomainValidator.validate(errors, model)){
            return "seats/add";
        }

        Theater theater = theaterService.findById(form.getTheaterId()).get();
        Seat seat = createSeat(form, theater);
        Seat savedSeat = seatService.save(seat);

        rttr.addFlashAttribute("savedSeat", savedSeat);
        rttr.addAttribute("theaterId", form.getTheaterId());
        return "redirect:/seats/list";
    }

    @GetMapping("/view")
    public String view(@ModelAttribute("seatId") Long seatId,
                       @ModelAttribute("pageVO") PageVO pageVO,
                       Model model){
        seatService.findById(seatId)
                   .ifPresent(vo->model.addAttribute("vo", vo));
        return "seats/view";
    }

    @GetMapping("/modify")
    public String modifyForm(@ModelAttribute("seatId") Long seatId,
                             @ModelAttribute("pageVO") PageVO pageVO,
                             Model model){
        seatService.findById(seatId)
                   .ifPresent(vo->model.addAttribute("form", new SeatForm(vo)));
        return "seats/modify";
    }

    @PostMapping("/modify")
    public String modify(SeatForm form,
                         Long theaterId,
                         RedirectAttributes rttr){
        log.info("modify : " + form);
        Theater theater = theaterService.findById(theaterId).get();
        seatService.findById(form.getId()).ifPresent(origin->{
            origin.changeInfo(form, theater);
            Seat modifiedSeat = seatService.save(origin);
            rttr.addFlashAttribute("modifiedSeat", modifiedSeat);
            rttr.addFlashAttribute("msg", "success");
            rttr.addAttribute("id", origin.getId());
        });

        rttr.addAttribute("theaterId", theaterId);
        return "redirect:/seats/list";
    }

    @PostMapping("/delete")
    public String delete(Long theaterId,
                         SeatForm form,
                         RedirectAttributes rttr){
        seatService.deleteById(form.getId());
        rttr.addFlashAttribute("msg", "success");
        rttr.addAttribute("theaterId", theaterId);
        return "redirect:/seats/list";
    }

    @PostMapping("/deletes")
    public String deletes(Long theaterId,
                          @RequestParam(value = "checks") List<Long> seatIds,
                          RedirectAttributes rttr){
        seatService.deleteAllById(seatIds);
        rttr.addFlashAttribute("msg", "success");
        rttr.addAttribute("theaterId", theaterId);
        return "redirect:/seats/list";
    }
}
