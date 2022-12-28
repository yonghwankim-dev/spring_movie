package kr.yh.movie.domain;

import kr.yh.movie.controller.cinema.CinemaDTO;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.service.CinemaService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.Assertions.*;

public class CinemaTest {
    @Test
    public void testCreateCinema(){
        //given
        CinemaDTO cinemaDTO = CinemaDTO.createCinemaDTO();
        cinemaDTO.setId(1L);
        cinemaDTO.setName("가산디지털");
        cinemaDTO.setLocation("서울");
        //when
        Cinema cinema = Cinema.createCinema(cinemaDTO);
        //then
        assertThat(cinema.getId()).isEqualTo(cinemaDTO.getId());
        assertThat(cinema.getName()).isEqualTo(cinemaDTO.getName());
        assertThat(cinema.getLocation()).isEqualTo(cinemaDTO.getLocation());
    }
    
    @Test
    public void testChangeInfo(){
        //given
        CinemaDTO cinemaDTO = CinemaDTO.createCinemaDTO();
        cinemaDTO.setId(1L);
        cinemaDTO.setName("가산디지털");
        cinemaDTO.setLocation("서울");
        Cinema cinema = Cinema.builder().build();
        //when
        cinema.changeInfo(cinemaDTO);
        //then
        assertThat(cinema.getId()).isEqualTo(cinemaDTO.getId());
        assertThat(cinema.getName()).isEqualTo(cinemaDTO.getName());
        assertThat(cinema.getLocation()).isEqualTo(cinemaDTO.getLocation());
    }
}
