package kr.yh.movie.controller.ticket;

import kr.yh.movie.controller.cinema.CinemaLocationDTO;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.Movie;
import kr.yh.movie.repository.CinemaRepository;
import kr.yh.movie.repository.MovieRepository;
import kr.yh.movie.service.CinemaService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TicketController.class,
        excludeFilters = @ComponentScan.Filter(type= FilterType.REGEX, pattern = "kr.yh.movie.controller.converter.*"))
@ActiveProfiles("local")
public class TicketControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CinemaRepository cinemaRepository;

    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private CinemaService cinemaService;

    @Test
    @WithMockUser
    public void testDepth1_whenFirstVisit() throws Exception {
        //given
        Cinema fakeCinema = createCinemaEntity(1L, "가산디지털", "서울");
        CinemaLocationDTO fakeCinemaLocationDTO = new CinemaLocationDTO("서울", 23L);
        List<Cinema> fakeCinemas = List.of(fakeCinema);
        List<CinemaLocationDTO> fakeCinemaLocations = List.of(fakeCinemaLocationDTO);
        String selectedCinemaId = "0";

        //mocking
        Mockito.when(cinemaService.findAll()).thenReturn(fakeCinemas);
        Mockito.when(cinemaRepository.findAllLocationAndCountGroupByLocation()).thenReturn(fakeCinemaLocations);
        Mockito.when(cinemaRepository.findById(Long.valueOf(selectedCinemaId))).thenReturn(Optional.ofNullable(null));

        //when
        ModelMap modelMap = this.mockMvc.perform(get("/ticket/depth1"))
                                    .andExpect(status().isOk())
                                    .andExpect(view().name("/ticket/depth1"))
                                    .andExpect(model().attributeExists("cinemas"))
                                    .andExpect(model().attributeExists("cinemaLocations"))
                                    .andReturn().getModelAndView().getModelMap();

        //then
        assertThat(modelMap.getAttribute("cinemas")).isEqualTo(fakeCinemas);
        assertThat(modelMap.getAttribute("cinemaLocations")).isEqualTo(fakeCinemaLocations);
    }

    private Cinema createCinemaEntity(long id, String name, String location) {
        return Cinema.builder()
                .id(id)
                .name(name)
                .location(location)
                .build();
    }

    @Test
    @WithMockUser
    public void testDepth1_whenSelectLocationAndCinema() throws Exception {
        //given
        String selectedCinemaId = "1";
        String selectedLocation = "서울";
        Cinema fakeSelectedCinema = new Cinema(1L, "가산디지털", "서울");
        List<Movie> fakeMovies = List.of(new Movie(1L, "올빼미", 15, 120));
        // mocking
        Mockito.when(cinemaService.findAll()).thenReturn(new ArrayList<>());
        Mockito.when(cinemaRepository.findAllLocationAndCountGroupByLocation()).thenReturn(new ArrayList<>());
        Mockito.when(cinemaRepository.findById(Long.valueOf(selectedCinemaId))).thenReturn(Optional.ofNullable(fakeSelectedCinema));
        Mockito.when(movieRepository.findAllByCinemaId(Long.valueOf(selectedCinemaId))).thenReturn(fakeMovies);
        //when
        ModelMap modelMap = this.mockMvc.perform(get("/ticket/depth1")
                        .param("selectedLocation", selectedLocation)
                        .param("selectedCinemaId", selectedCinemaId))
                .andExpect(status().isOk())
                .andExpect(view().name("/ticket/depth1"))
                .andExpect(model().attributeExists("cinemas"))
                .andExpect(model().attributeExists("cinemaLocations"))
                .andExpect(model().attributeExists("selectedLocation"))
                .andExpect(model().attributeExists("selectedCinemaId"))
                .andReturn().getModelAndView().getModelMap();
        //then
        assertThat(modelMap.getAttribute("selectedLocation")).isEqualTo(selectedLocation);
        assertThat(modelMap.getAttribute("selectedCinemaId")).isEqualTo(Long.valueOf(selectedCinemaId));
    }

}