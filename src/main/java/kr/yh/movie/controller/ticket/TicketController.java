package kr.yh.movie.controller.ticket;

import kr.yh.movie.domain.Cinema;
import kr.yh.movie.service.CinemaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class TicketController {
    private final CinemaService cinemaService;

    @GetMapping("/ticket/depth1")
    public ModelAndView depth1(){
        ModelAndView mav = new ModelAndView("/ticket/depth1");
        List<Cinema> cinemas = cinemaService.findAll();
        mav.getModelMap().addAttribute("cinemas", cinemas);
        return mav;
    }

    @GetMapping("/ticket/depth2")
    public ModelAndView depth2(){
        log.info("depth2");
        ModelAndView mav = new ModelAndView("/ticket/depth2");

        mav.getModel().put("seatTitleList", getSeatTitleList());
        return mav;
    }

    private List<String> getSeatTitleList(){
        List<String> seatTitleList = new ArrayList<>();
        for(int i = 65; i <= 75; i++){
            char alphabet = (char) i;
            seatTitleList.add(String.valueOf(alphabet));
        }
        return seatTitleList;
    }

    @GetMapping("/ticket/depth3")
    public ModelAndView depth3(){
        ModelAndView mav = new ModelAndView("/ticket/depth3");
        return mav;
    }

    @GetMapping("/ticket/depth4")
    public ModelAndView depth4(){
        ModelAndView mav = new ModelAndView("/ticket/depth4");
        return mav;
    }
}
