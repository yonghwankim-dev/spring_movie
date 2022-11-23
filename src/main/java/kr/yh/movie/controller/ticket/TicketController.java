package kr.yh.movie.controller.ticket;

import kr.yh.movie.controller.cinema.CinemaDTO;
import kr.yh.movie.controller.cinema.CinemaLocationDTO;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.Movie;
import kr.yh.movie.repository.CinemaRepository;
import kr.yh.movie.repository.MovieRepository;
import kr.yh.movie.service.CinemaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
public class TicketController {
    private final CinemaRepository cinemaRepository;
    private final CinemaService cinemaService;
    private final MovieRepository movieRepository;

    @GetMapping("/ticket/depth1")
    public ModelAndView depth1(@RequestParam(value = "selectedLocation", required = false, defaultValue = "서울") String selectedLocation,
                               @RequestParam(value = "selectedCinemaId", required = false, defaultValue = "0") Long selectedCinemaId){
        ModelAndView mav = new ModelAndView("/ticket/depth1");

        List<Cinema> cinemas = cinemaService.findAll();
        List<CinemaLocationDTO> cinemaLocations = cinemaRepository.findAllLocationAndCountGroupByLocation();
        List<Movie> moviesByCinema = selectedCinemaId == 0L ?
                movieRepository.findAll() : movieRepository.findAllByCinemaId(selectedCinemaId);

        log.info("moviesByCinema : " + moviesByCinema);

        mav.getModelMap().addAttribute("cinemas", cinemas);
        mav.getModelMap().addAttribute("cinemaLocations", cinemaLocations);
        mav.getModelMap().addAttribute("selectedLocation", selectedLocation);
        mav.getModelMap().addAttribute("selectedCinemaId", selectedCinemaId);
        mav.getModelMap().addAttribute("moviesByCinema", moviesByCinema);
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
