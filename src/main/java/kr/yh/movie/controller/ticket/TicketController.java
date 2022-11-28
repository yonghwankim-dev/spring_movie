package kr.yh.movie.controller.ticket;

import kr.yh.movie.controller.cinema.CinemaLocationDTO;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.Movie;
import kr.yh.movie.repository.cinema.CinemaRepository;
import kr.yh.movie.repository.cinema.CinemaRepositoryImpl;
import kr.yh.movie.repository.movie.MovieRepository;
import kr.yh.movie.repository.movie.MovieRepositoryImpl;
import kr.yh.movie.service.CinemaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.format.annotation.DateTimeFormat.ISO.*;

@RestController
@AllArgsConstructor
@Slf4j
public class TicketController {
    private final CinemaRepository cinemaRepository;
    private final CinemaRepositoryImpl cinemaRepositoryImpl;
    private final MovieRepositoryImpl movieRepositoryImpl;
    private final MovieRepository movieRepository;

    @GetMapping("/ticket/depth1")
    public ModelAndView depth1(@RequestParam(value = "selectedLocation", required = false, defaultValue = "서울") String location,
                               @RequestParam(value = "selectedCinemaId", required = false) Long cinemaId,
                               @RequestParam(value = "selectedMovieId", required = false) Long movieId,
                               @RequestParam(value = "selectedStartDate", required = false) @DateTimeFormat(iso = DATE_TIME) LocalDateTime startDate) {
        ModelAndView mav = new ModelAndView("/ticket/depth1");
        startDate = startDate == null ? LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT) : startDate;

        List<Cinema> cinemas = cinemaRepository.findAll();
        List<Cinema> cinemasOnScreen = cinemaRepositoryImpl.findAll(location, startDate, null, movieId);
        List<CinemaLocationDTO> cinemaLocations = cinemaRepository.findAllLocationAndCountGroupByLocation();
        List<Movie> movies = movieRepository.findAll();
        List<Movie> moviesOnScreen = movieRepositoryImpl.findAllMovieOnScreen(location, startDate, cinemaId);
        LocalDate today = LocalDate.now();
        List<LocalDate> localDateList = today.datesUntil(today.plusWeeks(2))
                                             .collect(Collectors.toList());
        mav.getModelMap().addAttribute("cinemas", cinemas);
        mav.getModelMap().addAttribute("cinemasOnScreen", cinemasOnScreen);
        mav.getModelMap().addAttribute("cinemaLocations", cinemaLocations);
        mav.getModelMap().addAttribute("movies", movies);
        mav.getModelMap().addAttribute("moviesOnScreen", moviesOnScreen);
        mav.getModelMap().addAttribute("selectedLocation", location);
        mav.getModelMap().addAttribute("selectedCinemaId", cinemaId);
        mav.getModelMap().addAttribute("selectedMovieId", movieId);
        mav.getModelMap().addAttribute("selectedStartDate", startDate);
        mav.getModelMap().addAttribute("localDateList", localDateList);

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
