package kr.yh.movie.controller.ticket;

import kr.yh.movie.controller.cinema.CinemaLocationDTO;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.repository.CinemaRepository;
import kr.yh.movie.service.CinemaService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.ui.ModelMap;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TicketController.class,
        excludeFilters = @ComponentScan.Filter(type= FilterType.REGEX, pattern = "kr.yh.movie.controller.converter.*"))
public class TicketControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CinemaRepository cinemaRepository;

    @MockBean
    private CinemaService cinemaService;

    @Test
    @WithMockUser
    public void testDepth1() throws Exception {
        //given
        Cinema fakeCinema = createCinemaEntity(1L, "가산디지털", "서울");
        CinemaLocationDTO fakeCinemaLocationDTO = new CinemaLocationDTO("서울", 23L);
        List<Cinema> fakeCinemas = List.of(fakeCinema);
        List<CinemaLocationDTO> fakeCinemaLocations = List.of(fakeCinemaLocationDTO);
        //mocking
        Mockito.when(cinemaService.findAll()).thenReturn(fakeCinemas);
        Mockito.when(cinemaRepository.findAllLocationAndCountGroupByLocation()).thenReturn(fakeCinemaLocations);
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

}