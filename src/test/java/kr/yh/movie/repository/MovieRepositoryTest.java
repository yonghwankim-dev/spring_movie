package kr.yh.movie.repository;

import kr.yh.movie.config.QuerydslConfig;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.Movie;
import kr.yh.movie.repository.cinema.CinemaRepository;
import kr.yh.movie.repository.movie.MovieRepository;
import kr.yh.movie.repository.movie.MovieRepositoryImpl;
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
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfig.class)
public class MovieRepositoryTest {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieRepositoryImpl movieRepositoryImpl;

    @Autowired
    CinemaRepository cinemaRepository;

    @Test
    @DisplayName("지역 + 상영일")
    public void testFindAllMovieOnScreenByLocationAndStartDate(){
        //given
        String location = "서울";
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        //when
        List<Movie> actual = movieRepositoryImpl.findAllMovieOnScreen(location, startDate);
        //then
        assertThat(actual.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("지역 + 상영일 + 영화관")
    public void testFindAllMovieOnScreenByLocationAndStartDateAndCinema(){
        //given
        String location = "서울";
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        Long cinemaId = 1L;
        //when
        List<Movie> actual = movieRepositoryImpl.findAllMovieOnScreen(location, startDate, cinemaId);
        //then
        assertThat(actual.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("지역 + 상영일 + 영화관들")
    public void testFindAllMovieOnScreenByLocationAndStartDateAndCinemaList(){
        //given
        String location = "서울";
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
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
        String location = "서울";
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        Long cinemaId = 1L;
        Long movieId = 1L;
        //when
        List<Movie> actual = movieRepositoryImpl.findAllMovieOnScreen(location, startDate, cinemaId, movieId);
        //then
        assertThat(actual.size()).isEqualTo(1);
    }

}