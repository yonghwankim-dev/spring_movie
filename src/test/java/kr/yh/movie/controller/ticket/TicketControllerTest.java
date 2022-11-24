package kr.yh.movie.controller.ticket;

import kr.yh.movie.controller.cinema.CinemaLocationDTO;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.Movie;
import kr.yh.movie.domain.Screen;
import kr.yh.movie.domain.Theater;
import kr.yh.movie.repository.CinemaRepository;
import kr.yh.movie.repository.MovieRepository;
import kr.yh.movie.repository.ScreenRepository;
import kr.yh.movie.service.CinemaService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static kr.yh.movie.controller.ticket.TicketController.*;
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
    private ScreenRepository screenRepository;
    @MockBean
    private CinemaService cinemaService;

    private String selectedLocation;
    private String selectedCinemaId;
    private Cinema fakeSelectedCinema;
    private Movie fakeMovie;
    private Theater fakeTheater;
    private Screen fakeScreen;
    private List<Movie> fakeMoviesOnScreen;
    private List<Screen> fakeScreensByCinemaId;
    private List<Long> fakeMovieIdsOnScreen;
    @BeforeEach
    public void setup(){
        selectedCinemaId = "1";
        selectedLocation = "서울";
        fakeSelectedCinema = new Cinema(1L, "가산디지털", "서울");
        fakeMovie = new Movie(1L, "올빼미", 15, 120);
        fakeTheater = new Theater(1L, "1관", fakeSelectedCinema);
        fakeScreen = new Screen(1L, LocalDateTime.now(), 1, new ArrayList<>(), fakeMovie, fakeTheater);
        fakeMoviesOnScreen = List.of(fakeMovie);
        fakeScreensByCinemaId = List.of(fakeScreen);
        fakeMovieIdsOnScreen = List.of(1L);

        Mockito.when(cinemaService.findAll()).thenReturn(new ArrayList<>());
        Mockito.when(cinemaRepository.findAllLocationAndCountGroupByLocation()).thenReturn(new ArrayList<>());
        Mockito.when(cinemaRepository.findById(Long.valueOf(selectedCinemaId))).thenReturn(Optional.ofNullable(fakeSelectedCinema));
        Mockito.when(screenRepository.findAllMovieByLocation(selectedLocation)).thenReturn(fakeMoviesOnScreen);
        Mockito.when(screenRepository.findAllByCinemaId(fakeSelectedCinema.getId())).thenReturn(fakeScreensByCinemaId);
        Mockito.when(screenRepository.findAllMovieIdByCinemaId(Long.valueOf(selectedCinemaId))).thenReturn(fakeMovieIdsOnScreen);
    }

    @Test
    @WithMockUser
    public void testDepth1_whenSelectLocation() throws Exception {
        //given
        Cinema fakeCinema = createCinemaEntity(1L, "가산디지털", "서울");
        CinemaLocationDTO fakeCinemaLocationDTO = new CinemaLocationDTO("서울", 23L);
        List<Cinema> fakeCinemas = List.of(fakeCinema);
        List<CinemaLocationDTO> fakeCinemaLocations = List.of(fakeCinemaLocationDTO);
        List<Movie> fakeMoviesOnScreen = List.of(fakeMovie);
        String selectedCinemaId = "0";

        //mocking
        Mockito.when(cinemaService.findAll()).thenReturn(fakeCinemas);
        Mockito.when(cinemaRepository.findAllLocationAndCountGroupByLocation()).thenReturn(fakeCinemaLocations);
        Mockito.when(cinemaRepository.findById(Long.valueOf(selectedCinemaId))).thenReturn(Optional.ofNullable(null));
        Mockito.when(screenRepository.findAllMovieByLocation(selectedLocation)).thenReturn(fakeMoviesOnScreen);

        //when
        ModelMap modelMap = this.mockMvc.perform(get("/ticket/depth1"))
                                    .andExpect(status().isOk())
                                    .andExpect(view().name("/ticket/depth1"))
                                    .andExpect(model().attributeExists("cinemas"))
                                    .andExpect(model().attributeExists("cinemaLocations"))
                                    .andExpect(model().attributeExists("moviesOnScreen"))
                                    .andReturn().getModelAndView().getModelMap();

        //then
        assertThat(modelMap.getAttribute("cinemas")).isEqualTo(fakeCinemas);
        assertThat(modelMap.getAttribute("cinemaLocations")).isEqualTo(fakeCinemaLocations);
        assertThat(modelMap.getAttribute("moviesOnScreen")).isEqualTo(fakeMoviesOnScreen);
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

        // mocking

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
                .andExpect(model().attributeExists("moviesOnScreen"))
                .andExpect(model().attributeExists("screensByCinemaId"))
                .andExpect(model().attributeExists("movieIdsOnScreen"))
                .andReturn().getModelAndView().getModelMap();
        //then
        assertThat(modelMap.getAttribute("selectedLocation")).isEqualTo(selectedLocation);
        assertThat(modelMap.getAttribute("selectedCinemaId")).isEqualTo(Long.valueOf(selectedCinemaId));
        assertThat(modelMap.getAttribute("moviesOnScreen")).isEqualTo(fakeMoviesOnScreen);
        assertThat(modelMap.getAttribute("screensByCinemaId")).isEqualTo(fakeScreensByCinemaId);
        assertThat(modelMap.getAttribute("movieIdsOnScreen")).isEqualTo(fakeMovieIdsOnScreen);
    }

    @Test
    @WithMockUser
    public void testDepth1_whenSelectLocationAndCinemaAndMovie() throws Exception {
        //given
        String selectedMovieId = "1";
        //when
        ModelMap modelMap = this.mockMvc.perform(get("/ticket/depth1")
                                            .param("selectedLocation", selectedLocation)
                                            .param("selectedCinemaId", selectedCinemaId)
                                            .param("selectedMovieId", selectedMovieId))
                                        .andExpect(model().attributeExists("selectedMovieId"))
                                        .andReturn().getModelAndView().getModelMap();
        //then
        assertThat(modelMap.getAttribute("selectedMovieId")).isEqualTo(Long.valueOf(selectedMovieId));
    }

    @Test
    @WithMockUser
    public void testDepth1_whenSelectLocationAndMovie() throws Exception {
        //given
        List<Long> cinemaIdsOnScreen = List.of(1L);
        String selectedMovieId = "1";
        //mocking
        Mockito.when(screenRepository.findAllCinemaIdByMovieId(Long.valueOf(selectedMovieId))).thenReturn(cinemaIdsOnScreen);

        //when
        ModelMap modelMap = this.mockMvc.perform(get("/ticket/depth1")
                                            .param("selectedLocation", selectedLocation)
                                            .param("selectedMovieId", selectedMovieId))
                                        .andExpect(model().attributeExists("cinemaIdsOnScreen"))
                                        .andReturn().getModelAndView().getModelMap();
        //then
        assertThat(modelMap.getAttribute("cinemaIdsOnScreen")).isEqualTo(cinemaIdsOnScreen);
    }


}