package kr.yh.movie.controller.myPage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MyPageController {
    @GetMapping("/myPage/myPay/ticketingOrderList")
    public ModelAndView ticketOrderList(){
        ModelAndView mav = new ModelAndView("/myPage/myPay/ticketingOrderList");
        return mav;
    }
}
