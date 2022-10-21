package kr.yh.movie.controller;

import kr.yh.movie.controller.theater.TheaterForm;
import kr.yh.movie.domain.Theater;
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
public class TheaterControllerTest {
    @Autowired
    TheaterService theaterService;
    @Autowired
    MockMvc mockMvc;
    
    @Test
    public void testList() throws Exception {
        //given
        String url = "/theaters/list";
        String cinemaId = "1";
        //when
        PageMarker<Page<Theater>> result = (PageMarker<Page<Theater>>) this.mockMvc.perform(get(url)
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
    public void testAddForm() throws Exception {
        //given
        String url = "/theaters/add";
        String cinemaId = "1";
        //when
        TheaterForm form = (TheaterForm) this.mockMvc.perform(get(url)
                                                     .param("cinemaId", cinemaId)
                                                     .contentType(TEXT_HTML))
                                                     .andExpect(status().isOk())
                                                     .andReturn().getModelAndView().getModel().get("form");
        //then
        assertThat(form).isNotNull();
    }

    @Test
    @Transactional
    public void testAdd(){
        //given
        String url = "/theaters/add";

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
