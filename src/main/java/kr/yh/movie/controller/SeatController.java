package kr.yh.movie.controller;

import kr.yh.movie.domain.Seat;
import kr.yh.movie.domain.Theater;
import kr.yh.movie.service.SeatService;
import kr.yh.movie.service.TheaterService;
import kr.yh.movie.util.SeatUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/seats")
@RequiredArgsConstructor
@Log
public class SeatController {
    private final SeatService seatService;
    private final TheaterService theaterService;
    @GetMapping("/modify")
    public String modifyForm(@ModelAttribute("theaterId") Long theaterId,
                             @ModelAttribute("cinemaId") Long cinemaId,
                             Model model){
        log.info("modifyForm");

        return "seats/modify";
    }

    @PostMapping("/modify")
    public String modify(SeatModifyForm form,
                         Long theaterId,
                         Long cinemaId,
                         RedirectAttributes rttr){
        log.info("modify : " + form.getSeat());

        return "redirect:/theaters/view";
    }
}
