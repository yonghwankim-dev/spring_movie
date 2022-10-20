package kr.yh.movie.controller;


import kr.yh.movie.controller.movie.MovieForm;
import kr.yh.movie.domain.Movie;
import kr.yh.movie.service.MovieService;
import kr.yh.movie.vo.PageMarker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional(readOnly = true)
public class MovieControllerTest {
    @Autowired
    MovieService movieService;

    @Autowired
    MockMvc mockMvc;
    
    @Test
    public void testList() throws Exception {
        //given
        String url = "/movies/list";
        //when
        PageMarker<Page<Movie>> result = (PageMarker<Page<Movie>>) this.mockMvc.perform(get(url)
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
        String url = "/movies/add";
        //when
        MovieForm form = (MovieForm) this.mockMvc.perform(get(url)
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
        String url = "/movies/add";
        String name = "공조";
        String filmRating = "15";
        String runtime = "120";
        //when
        Movie movie = (Movie) this.mockMvc.perform(post(url)
                                          .param("name", name)
                                          .param("filmRating", filmRating)
                                          .param("runtime", runtime)
                                          .contentType(APPLICATION_JSON)
                                          .with(csrf()))
                                          .andExpect(status().is3xxRedirection())
                                          .andReturn().getFlashMap().get("movie");
        //then
        assertThat(movie.getName()).isEqualTo(name);
    }
    
    @Test
    public void testView() throws Exception {
        //given
        String url = "/movies/view";
        String movieId = "1";
        //when
        Movie movie = (Movie) this.mockMvc.perform(get(url)
                                          .param("movieId", movieId)
                                          .contentType(TEXT_HTML))
                                          .andExpect(status().isOk())
                                          .andReturn().getModelAndView().getModel().get("vo");
        //then
        assertThat(movie.getId()).isEqualTo(Long.parseLong(movieId));
    }
    
    @Test
    public void testModifyForm() throws Exception {
        //given
        String url = "/movies/modify";
        String movieId = "1";
        //when
        MovieForm form = (MovieForm) this.mockMvc.perform(get(url)
                                                 .param("movieId", movieId)
                                                 .contentType(TEXT_HTML))
                                                 .andExpect(status().isOk())
                                                 .andReturn().getModelAndView().getModel().get("form");
        //then
        assertThat(form.getId()).isEqualTo(Long.parseLong(movieId));
    }
    
    @Test
    @Transactional
    public void testModify() throws Exception {
        //given
        String url = "/movies/modify";
        String movieId = "1";
        String name = "공조";
        String filmRating = "15";
        String runtime = "120";
        //when
        Movie modifiedMovie = (Movie) this.mockMvc.perform(post(url)
                                                  .param("id", movieId)
                                                  .param("name", name)
                                                  .param("filmRating", filmRating)
                                                  .param("runtime", runtime)
                                                  .contentType(APPLICATION_JSON)
                                                  .with(csrf()))
                                                  .andExpect(status().is3xxRedirection())
                                                  .andReturn().getFlashMap().get("modifiedMovie");
        //then
        assertThat(modifiedMovie.getId()).isEqualTo(Long.parseLong(movieId));
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
