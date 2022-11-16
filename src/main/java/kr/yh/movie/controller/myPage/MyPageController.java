package kr.yh.movie.controller.myPage;

import kr.yh.movie.controller.member.MemberDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MyPageController {
    @GetMapping("/myPage/myPay/ticketingOrderList")
    public ModelAndView ticketOrderList(){
        ModelAndView mav = new ModelAndView("/myPage/myPay/ticketingOrderList");
        mav.getModelMap().addAttribute("mTab", 0);
        mav.getModelMap().addAttribute("sTab", 0);
        return mav;
    }

    @GetMapping("/myPage/myInfo/myInfoList")
    public ModelAndView myInfoList(){
        ModelAndView mav = new ModelAndView("/myPage/myInfo/myInfoList");
        mav.getModelMap().addAttribute("mTab", 1);
        mav.getModelMap().addAttribute("sTab", 0);
        return mav;
    }

    @GetMapping("/myPage/myInfo/changeMyInfo/depth1")
    public ModelAndView changeMyInfoDepth1(){
        ModelAndView mav = new ModelAndView("/myPage/myInfo/myInfo_change/depth1");
        return mav;
    }

    @GetMapping("/myPage/myInfo/changeMyInfo/depth2")
    public ModelAndView changeMyInfoDepth2(){
        ModelAndView mav = new ModelAndView("/myPage/myInfo/myInfo_change/depth2");
        mav.getModelMap().addAttribute("form", new MemberDTO());
        return mav;
    }
}
