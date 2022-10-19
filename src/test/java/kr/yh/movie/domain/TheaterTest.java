package kr.yh.movie.domain;

import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.Theater;
import kr.yh.movie.service.CinemaService;
import kr.yh.movie.service.TheaterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TheaterTest {

    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private TheaterService theaterService;

    @Test
    public void insert(){
        //given
        Long cinemaId = 1L;
        Cinema cinema = cinemaService.findById(cinemaId).get();
        //when
        Theater theater = Theater.builder()
                                 .name("1관")
                                 .cinema(cinema)
                                 .build();
        theaterService.save(theater);
        //then
    }
}
