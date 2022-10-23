package kr.yh.movie.controller.reservation;


import kr.yh.movie.controller.screen.ScreenForm;
import kr.yh.movie.domain.Movie;
import kr.yh.movie.domain.Reservation;
import kr.yh.movie.domain.Screen;
import kr.yh.movie.domain.Theater;
import kr.yh.movie.service.ReservationService;
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

import static kr.yh.movie.domain.Screen.createScreen;

@Controller
@RequestMapping("/reservations")
@SessionAttributes("cinemaId")
@RequiredArgsConstructor
@Log
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/list")
    public String list(@ModelAttribute("cinemaId") Long cinemaId,
                       @ModelAttribute("pageVO") PageVO pageVO,
                       Model model){
        Pageable page = pageVO.makePageable(0, "id");
        Page<Reservation> result = reservationService.findAll(reservationService.makePredicates(pageVO.getType(), pageVO.getKeyword(), cinemaId), page);
        model.addAttribute("result", new PageMarker<>(result));
        return "reservations/list";
    }

    @GetMapping("/view")
    public String view(Long reservationId,
                       @ModelAttribute("pageVO") PageVO pageVO,
                       Model model){
        reservationService.findById(reservationId)
                          .ifPresent(vo->model.addAttribute("vo", vo));
        return "reservations/view";
    }
}
