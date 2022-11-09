package kr.yh.movie.controller.ticket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
public class TicketController {
    @GetMapping("/ticket/depth1")
    public ModelAndView depth1(){
        log.info("depth1");
        ModelAndView mav = new ModelAndView("/ticket/depth1");
        return mav;
    }

    @GetMapping("/ticket/depth2")
    public ModelAndView depth2(){
        log.info("depth2");
        ModelAndView mav = new ModelAndView("/ticket/depth2");
        return mav;
    }
}
