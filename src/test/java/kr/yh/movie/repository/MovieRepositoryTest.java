package kr.yh.movie.repository;

import kr.yh.movie.controller.movie.MovieDTO;
import kr.yh.movie.domain.Movie;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MovieRepositoryTest {
    @Autowired
    MovieRepository movieRepository;

    @Test
    @Transactional
    public void testFindAll(){
        //given
        List<Movie> expected = List.of(new Movie(1L, "올빼미", 15, 120),
                new Movie(2L, "블랙 팬서: 와칸다 포에버", 12, 120),
                new Movie(3L, "데시벨", 12, 130));
        //when
        List<Movie> actual = movieRepository.findAll();
        //then
        assertThat(actual).isEqualTo(expected);
    }
    
    @Test
    public void testFindAllByCinemaId(){
        //given
        Long cinemaId = 1L;
        List<Movie> expected = List.of(new Movie(1L, "올빼미", 15, 150));
        //when
        List<Movie> actual = movieRepository.findAllByCinemaId(cinemaId);
        //then
        assertThat(actual).isEqualTo(expected);
    }
}