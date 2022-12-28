package kr.yh.movie.domain;

import kr.yh.movie.controller.theater.TheaterForm;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.Theater;
import kr.yh.movie.service.CinemaService;
import kr.yh.movie.service.TheaterService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

public class TheaterTest {
    @Test
    @DisplayName("해당 상영관의 시네마가 설정되고 시네마도 상영관을 갖고 있는지 테스트")
    public void testSetCinema(){
        //given
        Cinema originCinema = Cinema.builder().id(1L).name("가산디지털").location("서울").build();
        Cinema newCinema = Cinema.builder().id(2L).name("가양").location("서울").build();
        Theater theater = Theater.builder().id(1L).name("1관").cinema(originCinema).build();
        originCinema.getTheaters().add(theater);
        //when
        theater.setCinema(newCinema);
        //then
        assertThat(newCinema.getTheaters().contains(theater)).isTrue();
        assertThat(theater.getCinema()).isEqualTo(newCinema);
    }

    @Test
    @DisplayName("TheaterForm과 Cinema가 주어질때 Theater 생성 테스트")
    public void testCreateTheater(){
        //given
        TheaterForm form = TheaterForm.builder().id(1L).name("1관").cinemaId(1L).build();
        Cinema cinema = Cinema.builder().id(1L).name("가산디지털").location("서울").build();
        //when
        Theater theater = Theater.createTheater(form, cinema);
        //then
        assertThat(theater.getId()).isEqualTo(form.getId());
        assertThat(theater.getName()).isEqualTo(form.getName());
        assertThat(theater.getCinema()).isEqualTo(cinema);
    }

    @Test
    @DisplayName("TheaterForm과 Cinema가 주어질때 Theater의 정보가 변경되는지 테스트")
    public void testChangeInfo(){
        //given
        TheaterForm form = TheaterForm.builder().id(1L).name("1관").cinemaId(1L).build();
        Cinema cinema = Cinema.builder().id(1L).name("가산디지털").location("서울").build();
        Theater theater = Theater.builder().id(1L).name("2관").build();
        //when
        theater.changeInfo(form, cinema);
        //then
        assertThat(theater.getId()).isEqualTo(form.getId());
        assertThat(theater.getName()).isEqualTo(form.getName());
        assertThat(theater.getCinema()).isEqualTo(cinema);
    }
}
