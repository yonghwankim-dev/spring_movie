package kr.yh.movie.controller.ticket;

import kr.yh.movie.domain.*;
import kr.yh.movie.repository.ScreenSeatRepository;
import kr.yh.movie.repository.TheaterRepository;
import kr.yh.movie.repository.cinema.CinemaRepository;
import kr.yh.movie.repository.cinema.CinemaRepositoryImpl;
import kr.yh.movie.repository.movie.MovieRepository;
import kr.yh.movie.repository.movie.MovieRepositoryImpl;
import kr.yh.movie.repository.screen.ScreenRepository;
import kr.yh.movie.repository.screen.ScreenRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TicketController.class,
        excludeFilters = @ComponentScan.Filter(type= FilterType.REGEX, pattern = "kr.yh.movie.controller.converter.*"))
@ActiveProfiles("dev")
public class TicketControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CinemaRepository cinemaRepository;

    @MockBean
    CinemaRepositoryImpl cinemaRepositoryImpl;

    @MockBean
    MovieRepositoryImpl movieRepositoryImpl;

    @MockBean
    ScreenRepository screenRepository;

    @MockBean
    MovieRepository movieRepository;

    @MockBean
    TheaterRepository theaterRepository;

    @MockBean
    ScreenSeatRepository screenSeatRepository;

    @MockBean
    ScreenRepositoryImpl screenRepositoryImpl;

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

    @Test
    @WithMockUser
    public void testDepth2() throws Exception {
        //given
        Long screenId = 1L;
        Movie movie = new Movie(1L, "올배미", 15, 120);
        Theater theater = new Theater(1L, "1관", new Cinema(1L, "가산디지털", "서울"));
        List<ScreenSeat> screenSeats = screenSeatRepository.findAllByScreenId(1L);
        Screen fakeScreen = new Screen(1L, LocalDateTime.now(), 1, screenSeats, movie, theater);

        //mocking
        Mockito.when(screenRepository.findById(screenId)).thenReturn(Optional.of(fakeScreen));

        //when
        ModelMap modelMap = this.mockMvc.perform(get("/ticket/depth2/")
                                            .param("screenId", String.valueOf(screenId)))
                                        .andExpect(status().isOk())
                                        .andExpect(model().attributeExists("screen"))
                                        .andExpect(view().name("/ticket/depth2"))
                                        .andReturn().getModelAndView().getModelMap();
        //then
    }

}