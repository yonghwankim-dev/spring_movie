package kr.yh.movie.service;

import kr.yh.movie.config.QuerydslConfig;
import kr.yh.movie.domain.*;
import kr.yh.movie.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ScreenServiceTest {
    @Autowired
    private ScreenService screenService;

    private Movie movie;
    private Cinema cinema;
    private Theater theater;

    @BeforeEach
    public void setup(){
        cinema = Cinema.builder().id(1L).name("가산디지털").location("서울").build();

        theater = Theater.builder().id(1L).name("1관").build();
        theater.setCinema(cinema);

        movie = Movie.builder().id(1L).name("올빼미").filmRating(15).runtime(120).build();
    }

    @Test
    public void testSave(){
        //given
        LocalDateTime startDateTime = LocalDateTime.now();
        Screen screen = Screen.builder()
                                .startDateTime(startDateTime)
                                .round(1)
                                .screenSeats(new ArrayList<>())
                                .build();
        screen.setTheater(theater);
        screen.setMovie(movie);
        //when
        Screen savedScreen = screenService.save(screen);
        //then
        assertThat(savedScreen).isEqualTo(screen);
    }
}