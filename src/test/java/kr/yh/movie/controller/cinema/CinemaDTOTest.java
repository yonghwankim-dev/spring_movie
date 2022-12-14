package kr.yh.movie.controller.cinema;

import kr.yh.movie.domain.Cinema;
import kr.yh.movie.repository.cinema.CinemaRepository;
import kr.yh.movie.service.CinemaService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CinemaDTOTest {
    @MockBean
    private CinemaService cinemaService;

    @MockBean
    private CinemaRepository cinemaRepository;

    @Test
    public void testCreateCinemaDTO(){
        CinemaDTO cinemaDTO = CinemaDTO.createCinemaDTO();
        assertThat(cinemaDTO.getId()).isEqualTo(null);
        assertThat(cinemaDTO.getName()).isEqualTo(null);
        assertThat(cinemaDTO.getLocation()).isEqualTo(null);
    }

    @Test
    public void testOf(){
        //given
        Long fakeCinemaId = 1L;
        Cinema fakeCinema = Cinema.builder()
                                  .id(1L)
                                  .name("가산디지털")
                                  .location("서울")
                                  .build();

        //mocking
        when(cinemaRepository.findById(fakeCinemaId)).thenReturn(Optional.ofNullable(fakeCinema));
        when(cinemaService.findById(fakeCinemaId)).thenReturn(Optional.ofNullable(fakeCinema));

        //when
        Cinema foundCinema = cinemaService.findById(fakeCinemaId).get();
        CinemaDTO ofCinemaDTO = CinemaDTO.of(foundCinema);

        //then
        assertThat(ofCinemaDTO.getId()).isEqualTo(fakeCinema.getId());
        assertThat(ofCinemaDTO.getName()).isEqualTo(fakeCinema.getName());
        assertThat(ofCinemaDTO.getLocation()).isEqualTo(fakeCinema.getLocation());
    }



}