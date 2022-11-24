package kr.yh.movie.controller.ticket;

import kr.yh.movie.controller.cinema.CinemaDTO;
import kr.yh.movie.controller.cinema.CinemaLocationDTO;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.Movie;
import kr.yh.movie.domain.Screen;
import kr.yh.movie.repository.CinemaRepository;
import kr.yh.movie.repository.MovieRepository;
import kr.yh.movie.repository.ScreenRepository;
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
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Slf4j
public class TicketController {
    private final CinemaRepository cinemaRepository;
    private final CinemaService cinemaService;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    @GetMapping("/ticket/depth1")
    public ModelAndView depth1(@RequestParam(value = "selectedLocation", required = false, defaultValue = "서울") String selectedLocation,
                               @RequestParam(value = "selectedCinemaId", required = false, defaultValue = "0") Long selectedCinemaId,
                               @RequestParam(value = "selectedMovieId", required = false, defaultValue = "0") Long selectedMovieId){
        ModelAndView mav = new ModelAndView("/ticket/depth1");

        List<Cinema> cinemas = cinemaService.findAll();
        List<CinemaLocationDTO> cinemaLocations = cinemaRepository.findAllLocationAndCountGroupByLocation();
        List<Movie> movies = movieRepository.findAll();
        List<Screen> screensByCinemaId = screenRepository.findAllByCinemaId(selectedCinemaId);
        List<Long> movieIdsOnScreen = screenRepository.findAllMovieIdByCinemaId(selectedCinemaId);

        mav.getModelMap().addAttribute("cinemas", cinemas);
        mav.getModelMap().addAttribute("cinemaLocations", cinemaLocations);
        mav.getModelMap().addAttribute("selectedLocation", selectedLocation);
        mav.getModelMap().addAttribute("selectedCinemaId", selectedCinemaId);
        mav.getModelMap().addAttribute("movies", movies);
        mav.getModelMap().addAttribute("screensByCinemaId", screensByCinemaId);
        mav.getModelMap().addAttribute("movieIdsOnScreen", movieIdsOnScreen);
        mav.getModelMap().addAttribute("selectedMovieId", selectedMovieId);
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
