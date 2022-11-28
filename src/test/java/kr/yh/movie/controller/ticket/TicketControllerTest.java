package kr.yh.movie.controller.ticket;

import kr.yh.movie.domain.Cinema;
import kr.yh.movie.repository.cinema.CinemaRepository;
import kr.yh.movie.repository.cinema.CinemaRepositoryImpl;
import kr.yh.movie.repository.movie.MovieRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ModelMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TicketController.class,
        excludeFilters = @ComponentScan.Filter(type= FilterType.REGEX, pattern = "kr.yh.movie.controller.converter.*"))
@ActiveProfiles("local")
public class TicketControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CinemaRepository cinemaRepository;

    @MockBean
    CinemaRepositoryImpl cinemaRepositoryImpl;

    @MockBean
    MovieRepositoryImpl movieRepositoryImpl;

    private List<Cinema> fakeCinemas;

    @BeforeEach
    public void setup(){
        Cinema cinema1 = Cinema.builder().id(1L).name("가산디지털").location("서울").build();
        Cinema cinema2 = Cinema.builder().id(1L).name("가양").location("서울").build();

        fakeCinemas = new ArrayList<>();
        fakeCinemas.add(cinema1);
        fakeCinemas.add(cinema2);
    }

    @Test
    @WithMockUser
    public void testDepth1() throws Exception {
        //given
        String location = "서울";
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        //mocking
        Mockito.when(cinemaRepositoryImpl.findAll("서울", startDate, null, null))
                .thenReturn(fakeCinemas);

        //when
        ModelMap modelMap = this.mockMvc.perform(get("/ticket/depth1"))
                                        .andExpect(status().isOk())
                                        .andReturn().getModelAndView().getModelMap();
        List<Cinema> cinemas = (List<Cinema>) modelMap.getAttribute("cinemas");
        //then
        Assertions.assertThat(cinemas.size()).isEqualTo(2);
    }
}