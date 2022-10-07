package kr.yh.movie.controller;

import kr.yh.movie.domain.Seat;
import kr.yh.movie.domain.Theater;
import kr.yh.movie.service.SeatService;
import kr.yh.movie.service.TheaterService;
import kr.yh.movie.util.RedirectAttributeUtil;
import kr.yh.movie.util.SeatUtil;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seats")
@SessionAttributes("cinemaId")
@RequiredArgsConstructor
@Log
public class SeatController {
    private final SeatService seatService;
    private final TheaterService theaterService;

    @GetMapping("/list")
    public String list(@ModelAttribute("theaterId") Long theaterId,
                       @ModelAttribute("pageVO") PageVO pageVO,
                       Model model){
        log.info("seat list");

        Pageable page = pageVO.makePageable(0, "id");
        Page<Seat> result = seatService.findAll(seatService.makePredicates(pageVO.getType(), pageVO.getKeyword(), theaterId), page);
        model.addAttribute("result", new PageMarker<>(result));
        return "seats/list";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute("theaterId") Long theaterId,
                          @ModelAttribute("pageVO") PageVO pageVO,
                          Model model){
        log.info("addForm");
        model.addAttribute("form", new SeatForm());
        return "seats/add";
    }

    @PostMapping("/add")
    public String add(Long theaterId,
                      @Valid @ModelAttribute SeatForm seatForm,
                      Errors errors,
                      Model model,
                      RedirectAttributes rttr) {
        log.info("seat add" + seatForm);
        if(errors.hasErrors()){
            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = seatService.validateHandling(errors);
            for(String key : validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key));
            }
            return "seats/add";
        }
        theaterService.findById(theaterId).ifPresent(vo->seatForm.setTheater(vo));
        Seat seat = Seat.createSeat(seatForm);
        seatService.save(seat);

        rttr.addAttribute("theaterId", theaterId);
        return "redirect:/seats/list";
    }

    @GetMapping("/view")
    public String view(@ModelAttribute("seatId") Long seatId,
                       @ModelAttribute("pageVO") PageVO pageVO,
                       Model model){
        log.info("view, seatId=" + seatId);
        seatService.findById(seatId).ifPresent(vo->model.addAttribute("vo", vo));
        return "seats/view";
    }

    @GetMapping("/modify")
    public String modifyForm(@ModelAttribute("seatId") Long seatId,
                             @ModelAttribute("pageVO") PageVO pageVO,
                             Model model){
        log.info("modifyForm");
        seatService.findById(seatId).ifPresent(vo->model.addAttribute("form", new SeatForm(vo)));

        return "seats/modify";
    }

    @PostMapping("/modify")
    public String modify(SeatForm form,
                         Long theaterId,
                         PageVO pageVO,
                         RedirectAttributes rttr){
        log.info("modify : " + form);

        theaterService.findById(theaterId).ifPresent(vo->form.setTheater(vo));
        seatService.findById(form.getId()).ifPresent(origin->{
            origin.changeInfo(form);
            seatService.save(origin);
            rttr.addFlashAttribute("msg", "success");
            rttr.addAttribute("id", origin.getId());
        });

        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);
        rttr.addAttribute("theaterId", theaterId);
        return "redirect:/seats/list";
    }

    @PostMapping("/delete")
    public String delete(SeatForm form,
                         @ModelAttribute("theaterId") Long theaterId,
                         RedirectAttributes rttr,
                         Model model){
        log.info("DELETE SEAT, seatId=" + form.getId());
        seatService.deleteById(form.getId());

        rttr.addFlashAttribute("msg", "success");

        // 페이징과 검색했던 결과로 이동하는 경우
        rttr.addAttribute("theaterId", theaterId);
        return "redirect:/seats/list";
    }

    @PostMapping("/deletes")
    public String deletes(@ModelAttribute("theaterId") Long theaterId,
                          @RequestParam(value = "checks") List<Long> ids,
                          PageVO pageVO,
                          RedirectAttributes rttr){
        log.info("DELETE IDS : " + ids);

        seatService.deleteAllById(ids);

        rttr.addFlashAttribute("msg", "success");

        // 페이징과 검색했던 결과로 이동하는 경우
        rttr.addAttribute("theaterId", theaterId);
        RedirectAttributeUtil.addAttributesPage(pageVO, rttr);

        return "redirect:/seats/list";
    }
}
