package kr.yh.movie.controller;

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
    public void testAddForm(){
        //given
        String url = "/seats/add";

        //when

        //then
    }

    @Test
    @Transactional
    public void testAdd(){
        //given

        //when

        //then
    }

    @Test
    public void testView(){
        //given

        //when

        //then
    }

    @Test
    public void testModifyForm(){
        //given

        //when

        //then
    }

    @Test
    @Transactional
    public void testModify(){
        //given

        //when

        //then
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
