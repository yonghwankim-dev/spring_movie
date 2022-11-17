package kr.yh.movie.controller.myPage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/myPage/myPay")
public class myPayController {
    @GetMapping("ticketingOrderList")
    public ModelAndView ticketOrderList(){
        ModelAndView mav = new ModelAndView("/myPage/myPay/ticketingOrderList");
        mav.getModelMap().addAttribute("mTab", 0);
        mav.getModelMap().addAttribute("sTab", 0);
        return mav;
    }
}
