package kr.yh.movie.controller;

import kr.yh.movie.domain.Seat;
import kr.yh.movie.service.SeatService;
import kr.yh.movie.util.SeatUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/seats")
@RequiredArgsConstructor
@Log
public class SeatController {
    private final SeatService seatService;

    @GetMapping("/modify")
    public String modifyForm(@ModelAttribute("theaterId") Long theaterId,
                             @ModelAttribute("cinemaId") Long cinemaId,
                             Model model){
        log.info("modifyForm");
        List<Seat> findedSeats = seatService.findAllByTheaterId(theaterId);
        List<String> rows      = seatService.getSeatRowsByTheaterId(theaterId);
        List<String> cols      = seatService.getSeatColsByTheaterId(theaterId);

        List<List<Seat>> seats = SeatUtil.to2DList(findedSeats, rows.size());
        model.addAttribute("seats", seats);
        model.addAttribute("rows", rows);
        model.addAttribute("cols", cols);
        return "seats/modify";
    }
}
