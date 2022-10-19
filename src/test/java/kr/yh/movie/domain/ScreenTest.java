package kr.yh.movie.domain;

import kr.yh.movie.service.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ScreenTest {
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
    public void insert(){
        //given
        Movie movie = movieService.findById(10L).get();
        Theater theater = theaterService.findById(1L).get();
        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0));
        int round = 1;
        //when
        for(int i = 1; i <= 12; i++){
            Screen screen = Screen.builder()
                    .startDateTime(startDateTime)
                    .round(round)
                    .movie(movie)
                    .theater(theater)
                    .build();
            // 상영좌석 생성


            screenService.save(screen);

            startDateTime = startDateTime.plusHours(2);
            round++;
        }
        //then
    }

    @Test
    @Transactional
    public void register(){
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
        Long screenId = screenService.register(screen);
        Screen findScreen = screenService.findById(screenId).get();
        List<ScreenSeat> screenSeats = screenSeatService.findAllByScreenId(screenId);
        List<Seat> seats = seatService.findAllByTheaterId(theaterId);
        //then
        assertThat(findScreen.getId()).isEqualTo(screenId);
        assertThat(screenSeats.size()).isEqualTo(seats.size());
    }
}