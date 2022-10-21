package kr.yh.movie.controller.screenSeat;

import kr.yh.movie.domain.ScreenSeat;
import kr.yh.movie.service.ScreenSeatService;
import kr.yh.movie.util.RedirectAttributeUtil;
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

@Controller
@RequestMapping("/screenSeats")
@RequiredArgsConstructor
@Log
public class ScreenSeatController {
    private final ScreenSeatService service;
    
    @GetMapping("/list")
    public String list(Long screenId,
                       @ModelAttribute("pageVO") PageVO pageVO,
                       Model model){
        Pageable page = pageVO.makePageable(0, "id");
        Page<ScreenSeat> result = service.findAll(service.makePredicates(pageVO.getType(), pageVO.getKeyword(), screenId), page);
        model.addAttribute("result", new PageMarker<>(result));
        return "screenSeats/list";
    }

    @GetMapping("/view")
    public String view(Long screenSeatId,
                       @ModelAttribute("pageVO") PageVO pageVO,
                       Model model){
        service.findById(screenSeatId)
               .ifPresent(vo->model.addAttribute("vo", vo));
        return "screenSeats/view";
    }

    @PostMapping("/delete")
    public String delete(Long screenSeatId,
                         RedirectAttributes rttr){
        service.deleteById(screenSeatId);
        rttr.addFlashAttribute("msg", "success");
        return "redirect:/screenSeats/list";
    }

    @PostMapping("/deletes")
    public String deletes(@RequestParam(value = "checks") List<Long> screenSeatIds,
                          RedirectAttributes rttr){
        service.deleteAllById(screenSeatIds);
        rttr.addFlashAttribute("msg", "success");
        return "redirect:/screenSeats/list";
    }
}
