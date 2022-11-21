package kr.yh.movie.controller;

import kr.yh.movie.controller.cinema.CinemaDTO;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.service.CinemaService;
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
public class CinemaControllerTest {
    @Autowired
    CinemaService cinemaService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testList() throws Exception {
        //given
        String url = "/cinemas/list";
        //when
        PageMarker<Page<Cinema>> result = (PageMarker<Page<Cinema>>) this.mockMvc.perform(get(url)
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
        String url = "/cinemas/add";
        //when
        CinemaDTO form = (CinemaDTO) this.mockMvc.perform(get(url)
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
        String url = "/cinemas/add";
        String name = "강남";
        String loc  = "서울";
        //when
        Cinema savedCinema = (Cinema) this.mockMvc.perform(post(url)
                                                  .param("name", name)
                                                  .param("loc", loc)
                                                  .contentType(APPLICATION_JSON)
                                                  .with(csrf()))
                                                  .andExpect(status().is3xxRedirection())
                                                  .andReturn().getFlashMap().get("savedCinema");
        //then
        assertThat(savedCinema.getName()).isEqualTo("강남");
    }
    
    @Test
    public void testView() throws Exception {
        //given
        String url = "/cinemas/view";
        String cinemaId = "1";
        //when
        Cinema foundCinema = (Cinema) this.mockMvc.perform(get(url)
                                                  .param("cinemaId", cinemaId)
                                                  .contentType(TEXT_HTML))
                                                  .andExpect(status().isOk())
                                                  .andReturn().getModelAndView().getModel().get("vo");
        //then
        assertThat(foundCinema.getId()).isEqualTo(Long.parseLong(cinemaId));
    }
    
    @Test
    public void testModifyForm() throws Exception {
        //given
        String url = "/cinemas/modify";
        String cinemaId = "1";
        //when
        CinemaDTO form = (CinemaDTO) this.mockMvc.perform(get(url)
                                                   .param("cinemaId", cinemaId)
                                                   .contentType(APPLICATION_JSON))
                                                   .andExpect(status().isOk())
                                                   .andReturn().getModelAndView().getModel().get("form");
        //then
        assertThat(form.getId()).isEqualTo(Long.parseLong(cinemaId));
    }
    
    @Test
    @Transactional
    public void testModify() throws Exception {
        //given
        String url = "/cinemas/modify";
        String cinemaId = "1";
        String modifiedName = "강서";
        String loc = "서울";
        //when
        Cinema modifiedCinema = (Cinema) this.mockMvc.perform(post(url)
                                             .param("id", cinemaId)
                                             .param("name", modifiedName)
                                             .param("loc", loc)
                                             .contentType(APPLICATION_JSON)
                                             .with(csrf()))
                                             .andExpect(status().is3xxRedirection())
                                             .andReturn().getFlashMap().get("modifiedCinema");
        //then
        assertThat(modifiedCinema.getName()).isEqualTo(modifiedName);
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        //given
        String url = "/cinemas/delete";
        String cinemaId = "1";
        //when
        String msg = (String) this.mockMvc.perform(post(url)
                                          .param("id", cinemaId)
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
        String url = "/cinemas/deletes";
        String[] cinemaIds = {"1"};
        //when
        String msg = (String) this.mockMvc.perform(post(url)
                                          .param("checks", cinemaIds)
                                          .contentType(APPLICATION_JSON)
                                          .with(csrf()))
                                          .andExpect(status().is3xxRedirection())
                                          .andReturn().getFlashMap().get("msg");
        //then
        assertThat(msg).isEqualTo("success");
    }
    
    @Test
    public void testHome() throws Exception {
        //given
        String url = "/cinemas/home";
        String cinemaId = "1";
        //when
        Cinema foundCinema = (Cinema) this.mockMvc.perform(get(url)
                                                  .param("cinemaId", cinemaId)
                                                  .contentType(TEXT_HTML))
                                                  .andExpect(status().isOk())
                                                  .andReturn().getModelAndView().getModel().get("vo");
        //then
        assertThat(foundCinema.getId()).isEqualTo(Long.parseLong(cinemaId));
    }

}
