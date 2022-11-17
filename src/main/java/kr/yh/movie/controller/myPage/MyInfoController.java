package kr.yh.movie.controller.myPage;

import kr.yh.movie.controller.member.MemberDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/myPage/myInfo")
public class MyInfoController {
    
    @GetMapping("/myInfoList")
    public ModelAndView myInfoList(){
        ModelAndView mav = new ModelAndView("/myPage/myInfo/myInfoList");
        mav.getModelMap().addAttribute("mTab", 1);
        mav.getModelMap().addAttribute("sTab", 0);
        return mav;
    }

    @GetMapping("/changeMyInfo/depth1")
    public ModelAndView changeMyInfoDepth1Get(){
        ModelAndView mav = new ModelAndView("/myPage/myInfo/myInfo_change/depth1");
        return mav;
    }

    @PostMapping("/changeMyInfo/depth1")
    public ModelAndView changeMyInfoDepth1Post(){
        ModelAndView mav = new ModelAndView("/myPage/myInfo/myInfo_change/depth2");
        return mav;
    }

    @GetMapping("/changeMyInfo/depth2")
    public ModelAndView changeMyInfoDepth2Get(){
        ModelAndView mav = new ModelAndView("/myPage/myInfo/myInfo_change/depth2");
        mav.getModelMap().addAttribute("form", new MemberDTO());
        return mav;
    }

    @PostMapping("/changeMyInfo/depth2")
    public ModelAndView changeMyInfoDepth2Post(){
        ModelAndView mav = new ModelAndView("/myPage/myInfo/myInfo_change/depth3");
        return mav;
    }

    @GetMapping("/changeMyInfo/depth3")
    public ModelAndView changeMyInfoDepth3Get(){
        ModelAndView mav = new ModelAndView("/myPage/myInfo/myInfo_change/depth3");
        return mav;
    }

    @GetMapping("/changeMyPassword/depth1")
    public ModelAndView changeMyPwdDepth1Get(){
        ModelAndView mav = new ModelAndView("/myPage/myInfo/mypassword_change/depth1");
        return mav;
    }

    @PostMapping("/changeMyPassword/depth1")
    public ModelAndView changeMyPwdDepth1Post(){
        ModelAndView mav = new ModelAndView("/myPage/myInfo/mypassword_change/depth2");
        return mav;
    }

    @GetMapping("/changeMyPassword/depth2")
    public ModelAndView changeMyPwdDepth2Get(){
        ModelAndView mav = new ModelAndView("/myPage/myInfo/mypassword_change/depth2");
        mav.getModelMap().addAttribute("form", new MemberDTO());
        return mav;
    }

    @PostMapping("/changeMyPassword/depth2")
    public ModelAndView changeMyPwdDepth2Post(){
        ModelAndView mav = new ModelAndView("/myPage/myInfo/mypassword_change/depth3");
        return mav;
    }

    @GetMapping("/changeMyPassword/depth3")
    public ModelAndView changeMyPwdDepth3Get(){
        ModelAndView mav = new ModelAndView("/myPage/myInfo/mypassword_change/depth3");
        return mav;
    }

    @GetMapping("/myInfoSecession/depth1")
    public ModelAndView deleteMemberDepth1Get(){
        ModelAndView mav = new ModelAndView("/myPage/myInfo/myInfo_secession/depth1");
        return mav;
    }

    @PostMapping("/myInfoSecession/depth1")
    public ModelAndView deleteMemberDepth1Post(){
        ModelAndView mav = new ModelAndView("/myPage/myInfo/myInfo_secession/depth2");
        return mav;
    }

    @GetMapping("/myInfoSecession/depth2")
    public ModelAndView deleteMemberDepth2Get(){
        ModelAndView mav = new ModelAndView("/myPage/myInfo/myInfo_secession/depth2");
        mav.getModelMap().addAttribute("form", new MemberDTO());
        return mav;
    }

    @PostMapping("/myInfoSecession/depth2")
    public ModelAndView deleteMemberDepth2Post(){
        ModelAndView mav = new ModelAndView("/myPage/myInfo/myInfo_secession/depth3");
        return mav;
    }

    @GetMapping("/myInfoSecession/depth3")
    public ModelAndView deleteMemberDepth3Get(){
        ModelAndView mav = new ModelAndView("/myPage/myInfo/myInfo_secession/depth3");
        return mav;
    }
}
