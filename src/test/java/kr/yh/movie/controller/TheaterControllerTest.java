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
    public void testAdd() throws Exception {
        //given
        String url = "/theaters/add";
        String name = "1관";
        String cinemaId = "1";
        //when
        Theater savedTheater = (Theater) this.mockMvc.perform(post(url)
                                                     .param("name", name)
                                                     .param("cinemaId", cinemaId)
                                                     .contentType(APPLICATION_JSON)
                                                     .with(csrf()))
                                                     .andExpect(status().is3xxRedirection())
                                                     .andReturn().getFlashMap().get("savedTheater");
        //then
        assertThat(savedTheater.getName()).isEqualTo(name);
    }

    @Test
    public void testView() throws Exception {
        //given
        String url = "/theaters/view";
        String cinemaId = "1";
        String theaterId = "1";
        //when
        Theater theater = (Theater) this.mockMvc.perform(get(url)
                                                .param("cinemaId", cinemaId)
                                                .param("theaterId", theaterId)
                                                .contentType(TEXT_HTML))
                                                .andExpect(status().isOk())
                                                .andReturn().getModelAndView().getModel().get("vo");
        //then
        assertThat(theater.getId()).isEqualTo(Long.parseLong(theaterId));
    }

    @Test
    public void testModifyForm() throws Exception {
        //given
        String url = "/theaters/modify";
        String cinemaId = "1";
        String theaterId = "1";
        //when
        TheaterForm form = (TheaterForm) this.mockMvc.perform(get(url)
                                                     .param("cinemaId", cinemaId)
                                                     .param("theaterId", theaterId))
                                                     .andExpect(status().isOk())
                                                     .andReturn().getModelAndView().getModel().get("form");
        //then
        assertThat(form.getId()).isEqualTo(Long.parseLong(theaterId));
    }

    @Test
    @Transactional
    public void testModify() throws Exception {
        //given
        String url = "/theaters/modify";
        String cinemaId = "1";
        String theaterId = "1";
        String modifiedName = "2관";
        //when
        Theater modifiedTheater = (Theater) this.mockMvc.perform(post(url)
                                                        .param("cinemaId", cinemaId)
                                                        .param("id", theaterId)
                                                        .param("name", modifiedName)
                                                        .contentType(APPLICATION_JSON)
                                                        .with(csrf()))
                                                        .andExpect(status().is3xxRedirection())
                                                        .andReturn().getFlashMap().get("modifiedTheater");
        //then
        assertThat(modifiedTheater.getName()).isEqualTo(modifiedName);
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        //given
        String url = "/theaters/delete";
        String cinemaId = "1";
        String theaterId = "1";
        //when
        String msg = (String) this.mockMvc.perform(post(url)
                                          .param("cinemaId", cinemaId)
                                          .param("theaterId", theaterId)
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
        String url = "/theaters/deletes";
        String cinemaId = "1";
        String[] theaterIds = {"1"};
        //when
        String msg = (String) this.mockMvc.perform(post(url)
                                          .param("cinemaId", cinemaId)
                                          .param("checks", theaterIds)
                                          .contentType(APPLICATION_JSON)
                                          .with(csrf()))
                                          .andExpect(status().is3xxRedirection())
                                          .andReturn().getFlashMap().get("msg");
        //then
        assertThat(msg).isEqualTo("success");
    }

}
