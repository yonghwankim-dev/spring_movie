package kr.yh.movie.domain.cinema;

import kr.yh.movie.domain.Cinema;
import kr.yh.movie.service.CinemaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest
@Commit
public class CinemaTest {
    @Autowired
    private CinemaService cinemaService;

    @Test
    public void insert(){
        //given

        //when
        for(int i = 1; i <= 30; i++){
            Cinema cinema = Cinema.builder()
                                    .name("영화관"+i)
                                    .location("지역"+i)
                                    .build();
            cinemaService.save(cinema);
        }
        //then
    }
}
