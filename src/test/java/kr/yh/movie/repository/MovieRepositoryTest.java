package kr.yh.movie.repository;

import kr.yh.movie.config.QuerydslConfig;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.Movie;
import kr.yh.movie.repository.cinema.CinemaRepository;
import kr.yh.movie.repository.movie.MovieRepository;
import kr.yh.movie.repository.movie.MovieRepositoryImpl;
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
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(QuerydslConfig.class)
public class MovieRepositoryTest {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieRepositoryImpl movieRepositoryImpl;

    @Autowired
    CinemaRepository cinemaRepository;

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
    public void testFindAllMovieOnScreenByLocationAndStartDate(){
        //given
        //when
        List<Movie> actual = movieRepositoryImpl.findAllMovieOnScreen(location, startDate);
        //then
        assertThat(actual.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("지역 + 상영일 + 영화관")
    public void testFindAllMovieOnScreenByLocationAndStartDateAndCinema(){
        //given
        //when
        List<Movie> actual = movieRepositoryImpl.findAllMovieOnScreen(location, startDate, cinemaId);
        //then
        assertThat(actual.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("지역 + 상영일 + 영화관들")
    public void testFindAllMovieOnScreenByLocationAndStartDateAndCinemaList(){
        //given
        List<Long> cinemaIdList = List.of(1L, 2L);
        //when
        List<Movie> actual = movieRepositoryImpl.findAllMovieOnScreen(location, startDate, cinemaIdList);
        //then
        assertThat(actual.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("지역 + 상영일 + 영화관 + 영화")
    public void testFindAllMovieOnScreenByLocationAndStartDateAndCinemaAndMovie(){
        //given
        //when
        List<Movie> actual = movieRepositoryImpl.findAllMovieOnScreen(location, startDate, cinemaId, movieId);
        //then
        assertThat(actual.size()).isEqualTo(1);
    }
}