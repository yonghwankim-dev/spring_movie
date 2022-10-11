package kr.yh.movie.domain;

import kr.yh.movie.service.MovieService;
import kr.yh.movie.service.ScreenService;
import kr.yh.movie.service.TheaterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ScreenTest {
    @Autowired
    private ScreenService screenService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private TheaterService theaterService;
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
            screenService.save(screen);

            startDateTime = startDateTime.plusHours(2);
            round++;
        }
        //then
    }
}