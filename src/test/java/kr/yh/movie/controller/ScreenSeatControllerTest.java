package kr.yh.movie.controller;

import kr.yh.movie.domain.Screen;
import kr.yh.movie.domain.ScreenSeat;
import kr.yh.movie.service.SeatService;
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
public class ScreenSeatControllerTest {
    @Autowired
    SeatService seatService;
    @Autowired
    MockMvc mockMvc;
    
    @Test
    public void testList() throws Exception {
        //given
        String url = "/screenSeats/list";
        String screenId = "1";
        //when
        PageMarker<Page<ScreenSeat>> result = (PageMarker<Page<ScreenSeat>>) this.mockMvc.perform(get(url)
                                                                                         .param("screenId", screenId)
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
        String url = "/screenSeats/view";
        String screenSeatId = "1";
        //when
        ScreenSeat screenSeat = (ScreenSeat) this.mockMvc.perform(get(url)
                                                         .param("screenSeatId", screenSeatId)
                                                         .contentType(TEXT_HTML))
                                                         .andExpect(status().isOk())
                                                         .andReturn().getModelAndView().getModel().get("vo");
        //then
        assertThat(screenSeat.getId()).isEqualTo(Long.parseLong(screenSeatId));
    }
    
    @Test
    @Transactional
    public void testDelete() throws Exception {
        //given
        String url = "/screenSeats/delete";
        String screenSeatId = "1";
        //when
        String msg = (String) this.mockMvc.perform(post(url)
                                          .param("screenSeatId", screenSeatId)
                                          .contentType(APPLICATION_JSON)
                                          .with(csrf()))
                                          .andExpect(status().is3xxRedirection())
                                          .andReturn().getFlashMap().get("msg");
        //then
        assertThat(msg).isEqualTo("success");
    }
    
    @Test
    @Transactional
    public void testDeletes() throws Exception {
        //given
        String url = "/screenSeats/deletes";
        String[] screenSeatIds = {"1"};
        //when
        String msg = (String) this.mockMvc.perform(post(url)
                                          .param("checks", screenSeatIds)
                                          .contentType(APPLICATION_JSON)
                                          .with(csrf()))
                                          .andExpect(status().is3xxRedirection())
                                          .andReturn().getFlashMap().get("msg");
        //then
        assertThat(msg).isEqualTo("success");
    }
}
