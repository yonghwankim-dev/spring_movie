package kr.yh.movie.controller;

import kr.yh.movie.controller.cinema.CinemaForm;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.service.CinemaService;
import kr.yh.movie.vo.PageMarker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;
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
        PageMarker<Page<Cinema>> result =
                (PageMarker<Page<Cinema>>) this.mockMvc.perform(get(url)
                                                       .contentType(MediaType.TEXT_HTML))
                                                       .andExpect(status().isOk())
                                                       .andReturn().getModelAndView().getModel().get("result");
        //then
        assertThat(result).isNotNull();
    }
    
    @Test
    public void testAddForm() throws Exception {
        //given
        String url = "/cinemas/add";
        //when
        CinemaForm form = (CinemaForm) this.mockMvc.perform(get(url)
                                                   .contentType(MediaType.TEXT_HTML))
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
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .with(csrf()))
                                                  .andExpect(status().is3xxRedirection())
                                                  .andReturn().getFlashMap().get("savedCinema");
        //then
        assertThat(savedCinema.getName()).isEqualTo("강남");
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
    public void testModify(){
        //given
        
        //when
        
        //then
    }
    
    @Test
    public void testDelete(){
        //given
        
        //when
        
        //then
    }
    
    @Test
    public void testDeletes(){
        //given
        
        //when
        
        //then
    }
}
