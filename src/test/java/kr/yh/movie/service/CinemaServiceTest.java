package kr.yh.movie.service;

import kr.yh.movie.domain.Cinema;
import kr.yh.movie.repository.CinemaRepository;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CinemaServiceTest {
    @MockBean
    private CinemaRepository cinemaRepository;
    
    @Autowired
    private CinemaService cinemaService;

    private Cinema cinema;

    @BeforeEach
    public void setup(){
        cinema = createCinemaEntity(1L, "가산디지털", "서울");
    }

    @Test
    @Transactional
    public void testSave(){
        //given

        //mocking
        when(cinemaRepository.save(cinema)).thenReturn(cinema);

        //when
        Cinema save = cinemaService.save(cinema);

        //then
        assertThat(save).isEqualTo(cinema);
    }

    @Test
    public void testFindAll(){
        //given

        //mocking
        when(cinemaRepository.findAll()).thenReturn(List.of(cinema));

        //when
        List<Cinema> cinemas = cinemaService.findAll();

        //then
        assertThat(cinemas).isEqualTo(List.of(cinema));
    }

    private Cinema createCinemaEntity(long id, String name, String location) {
        return Cinema.builder()
                .id(id)
                .name(name)
                .location(location)
                .build();
    }
}