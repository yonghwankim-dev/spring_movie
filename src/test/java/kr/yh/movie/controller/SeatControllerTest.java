package kr.yh.movie.controller;

import kr.yh.movie.controller.seat.SeatForm;
import kr.yh.movie.domain.Seat;
import kr.yh.movie.service.SeatService;
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
public class SeatControllerTest {
    @Autowired
    SeatService seatService;
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testList() throws Exception {
        //given
        String url = "/seats/list";
        String theaterId = "1";
        //when
        PageMarker<Page<Seat>> result = (PageMarker<Page<Seat>>) this.mockMvc.perform(get(url)
                                                                             .param("theaterId", theaterId)
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
    public void testAddForm() throws Exception {
        //given
        String url = "/seats/add";
        String theaterId = "1";
        //when
        SeatForm form = (SeatForm) this.mockMvc.perform(get(url)
                                               .param("theaterId", theaterId)
                                               .contentType(TEXT_HTML))
                                               .andExpect(status().isOk())
                                               .andReturn().getModelAndView().getModel().get("form");
        //then
        assertThat(form).isNotNull();
    }

    @Test
    @Transactional
    public void testAdd() throws Exception {
        //given
        String url = "/seats/add";
        String seat_row = "A";
        String seat_col = "1";
        String theaterId = "1";
        //when
        Seat savedSeat = (Seat) this.mockMvc.perform(post(url)
                                            .param("seat_row", seat_row)
                                            .param("seat_col", seat_col)
                                            .param("theaterId", theaterId)
                                            .contentType(APPLICATION_JSON)
                                            .with(csrf()))
                                            .andExpect(status().is3xxRedirection())
                                            .andReturn().getFlashMap().get("savedSeat");
        //then
        assertThat(savedSeat).isNotNull();
    }

    @Test
    public void testView() throws Exception {
        //given
        String url = "/seats/view";
        String seatId = "1";
        //when
        Seat seat = (Seat) this.mockMvc.perform(get(url)
                                       .param("seatId", seatId)
                                       .contentType(TEXT_HTML))
                                       .andExpect(status().isOk())
                                       .andReturn().getModelAndView().getModel().get("vo");
        //then
        assertThat(seat.getId()).isEqualTo(Long.parseLong(seatId));
    }

    @Test
    public void testModifyForm() throws Exception {
        //given
        String url = "/seats/modify";
        String seatId = "1";
        //when
        SeatForm form = (SeatForm) this.mockMvc.perform(get(url)
                                               .param("seatId", seatId)
                                               .contentType(TEXT_HTML))
                                               .andExpect(status().isOk())
                                               .andReturn().getModelAndView().getModel().get("form");
        //then
        assertThat(form.getId()).isEqualTo(Long.parseLong(seatId));
    }

    @Test
    @Transactional
    public void testModify() throws Exception {
        //given
        String url = "/seats/modify";
        String seatId = "1";
        String seat_row = "B";
        String seat_col = "1";
        String theaterId = "1";
        //when
        Seat modifiedSeat = (Seat) this.mockMvc.perform(post(url)
                                               .param("id", seatId)
                                               .param("seat_row", seat_row)
                                               .param("seat_col", seat_col)
                                               .param("theaterId", theaterId)
                                               .contentType(APPLICATION_JSON)
                                               .with(csrf()))
                                               .andExpect(status().is3xxRedirection())
                                               .andReturn().getFlashMap().get("modifiedSeat");
        //then
        assertThat(modifiedSeat.getSeat_row()).isEqualTo(seat_row);
    }

    @Test
    @Transactional
    public void testDelete(){
        //given

        //when

        //then
    }

    @Test
    @Transactional
    public void testDeletes(){
        //given

        //when

        //then
    }
}
