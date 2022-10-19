package kr.yh.movie.service;

import kr.yh.movie.domain.*;
import kr.yh.movie.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ScreenServiceTest {
    @Autowired
    private ScreenService screenService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private TheaterService theaterService;
    @Autowired
    private SeatService seatService;
    @Autowired
    private ScreenSeatService screenSeatService;

    @Test
    @Transactional
    public void testSave(){
        //given
        Long movieId = 1L;
        Long theaterId = 1L;
        Movie movie = movieService.findById(movieId).get();
        Theater theater = theaterService.findById(theaterId).get();
        Screen screen = Screen.builder()
                              .startDateTime(LocalDateTime.now())
                              .round(1)
                              .movie(movie)
                              .theater(theater)
                              .build();
        //when
        Screen savedScreen = screenService.save(screen);
        Screen findScreen = screenService.findById(savedScreen.getId()).get();
        List<ScreenSeat> screenSeats = screenSeatService.findAllByScreenId(savedScreen.getId());
        List<Seat> seats = seatService.findAllByTheaterId(theaterId);
        //then
        assertThat(findScreen.getId()).isEqualTo(savedScreen.getId());
        assertThat(screenSeats.size()).isEqualTo(seats.size());
    }
}