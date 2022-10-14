package kr.yh.movie.config;

import kr.yh.movie.controller.converter.LongToMovieConverter;
import kr.yh.movie.controller.converter.LongToTheaterConverter;
import kr.yh.movie.domain.Movie;
import kr.yh.movie.domain.Theater;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WebConfigTest {
    @Autowired
    LongToMovieConverter longToMovieConverter;
    @Autowired
    LongToTheaterConverter longToTheaterConverter;

    @Test
    public void whenConvertLongToMovie_thenSuccess(){
        //given
        String id = "1";
        //when
        Movie movie = longToMovieConverter.convert(id);
        //then
        assertThat(movie.getName()).isEqualTo("영화1");
    }

    @Test
    public void whenConvertLongToTheater_thenSuccess(){
        //given
        String id = "1";
        //when
        Theater theater = longToTheaterConverter.convert(id);
        //then
        assertThat(theater.getName()).isEqualTo("1관");
    }

}