package kr.yh.movie.repository.screen;

import kr.yh.movie.config.QuerydslConfig;
import kr.yh.movie.domain.Screen;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(QuerydslConfig.class)
public class ScreenRepositoryImplTest {
    @Autowired
    private ScreenRepositoryImpl screenRepositoryImpl;

    Long cinemaId;
    Long movieId;
    String location;
    LocalDateTime startDate;

    @BeforeEach
    public void setup(){
        cinemaId = 1L;
        movieId = 1L;
        location = "서울";
        startDate = LocalDateTime.of(LocalDate.of(2022, 01, 01), LocalTime.of(0,0));
    }

    @Test
    @DisplayName("지역 + 상영일")
    public void testFindAllByLocationAndStartDate(){
        //given
        //when
        List<Screen> actual = screenRepositoryImpl.findAll(location, startDate);
        //then
        assertThat(actual.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("지역 + 영화관 + 상영일")
    public void testFindAllByLocationAndCinemaIdAndStartDate(){
        //given
        //when
        List<Screen> actual = screenRepositoryImpl.findAll(location, cinemaId, startDate);
        //then
        assertThat(actual.size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("지역 + 영화관 + 상영일 + 영화")
    public void testFindAllByLocationAndCinemaIdAndStartDateAndMovie(){
        //given
        //when
        List<Screen> actual = screenRepositoryImpl.findAll(location, cinemaId, movieId, startDate);
        //then
        assertThat(actual.size()).isGreaterThan(0);
    }
}