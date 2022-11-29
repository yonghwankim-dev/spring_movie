package kr.yh.movie.repository.screen;

import kr.yh.movie.config.QuerydslConfig;
import kr.yh.movie.domain.Screen;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfig.class)
public class ScreenRepositoryImplTest {
    @Autowired
    private ScreenRepositoryImpl screenRepositoryImpl;


    @Test
    @DisplayName("지역 + 상영일")
    public void testFindAllByLocationAndStartDate(){
        //given
        String location = "서울";
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        //when
        List<Screen> actual = screenRepositoryImpl.findAll(location, startDate);
        //then
        Assertions.assertThat(actual.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("지역 + 영화관 + 상영일")
    public void testFindAllByLocationAndCinemaIdAndStartDate(){
        //given
        String location = "서울";
        Long cinemaId = 1L;
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        //when
        List<Screen> actual = screenRepositoryImpl.findAll(location, cinemaId, startDate);
        //then
        Assertions.assertThat(actual.size()).isEqualTo(24);
    }

    @Test
    @DisplayName("지역 + 영화관 + 상영일 + 영화")
    public void testFindAllByLocationAndCinemaIdAndStartDateAndMovie(){
        //given
        String location = "서울";
        Long cinemaId = 1L;
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        Long movieId = 1L;
        //when
        List<Screen> actual = screenRepositoryImpl.findAll(location, cinemaId, movieId, startDate);
        //then
        Assertions.assertThat(actual.size()).isEqualTo(8);
    }
}