package kr.yh.movie.controller;

import kr.yh.movie.domain.Reservation;
import kr.yh.movie.service.ReservationService;
import kr.yh.movie.service.TheaterService;
import kr.yh.movie.vo.PageMarker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional(readOnly = true)
public class ReservationTest {
    @Autowired
    ReservationService reservationService;
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testList() throws Exception {
        //given
        String url = "reservations/list";
        String cinemaId = "1";
        //when
        PageMarker<Page<Reservation>> result = (PageMarker<Page<Reservation>>) this.mockMvc.perform(get(url)
                                                                                        .param("cinemaId", cinemaId)
                                                                                        .contentType(TEXT_HTML))
                                                                                        .andExpect(status().isOk())
                                                                                        .andReturn()
                                                                                        .getModelAndView()
                                                                                        .getModel()
                                                                                        .get("result");
        //then
        assertThat(result).isNotNull();
    }

    @Test
    public void testView() throws Exception {
        //given
        String url = "reservations/view";
        String reservationId = "1";
        //when
        Reservation reservation = (Reservation) this.mockMvc.perform(get(url)
                                                            .param("reservationId", reservationId)
                                                            .contentType(TEXT_HTML))
                                                            .andExpect(status().isOk())
                                                            .andReturn().getModelAndView().getModel().get("vo");
        //then
        assertThat(reservation.getId()).isEqualTo(Long.parseLong(reservationId));
    }
}
