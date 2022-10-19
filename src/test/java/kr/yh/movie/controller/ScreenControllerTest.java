package kr.yh.movie.controller;

import kr.yh.movie.controller.screen.ScreenForm;
import kr.yh.movie.domain.Movie;
import kr.yh.movie.domain.Screen;
import kr.yh.movie.domain.Theater;
import kr.yh.movie.vo.PageMarker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional(readOnly = true)
public class ScreenControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testList() throws Exception {
        //given
        String url = "/screens/list";
        //when
        Object result = this.mockMvc.perform(get(url)
                                    .contentType(MediaType.TEXT_HTML))
                                    .andExpect(status().isOk())
                                    .andReturn().getModelAndView().getModel().get("result");
        //then
        assertThat(result).isNotNull();
    }
    
    @Test
    public void testAddForm() throws Exception {
        //given
        String url = "/screens/add";
        String cinemaId = "1";
        //when
        Map<String, Object> model = this.mockMvc.perform(get(url)
                                                .param("cinemaId", cinemaId)
                                                .contentType(MediaType.TEXT_HTML))
                                                .andExpect(status().isOk())
                                                .andReturn().getModelAndView().getModel();

        ScreenForm form = (ScreenForm) model.get("form");
        List<Movie> movies = (List<Movie>) model.get("movies");
        List<Theater> theaters = (List<Theater>) model.get("theaters");
        //then
        assertThat(form).isNotNull();
        assertThat(movies).isNotNull();
        assertThat(theaters).isNotNull();
    }
    
    @Test
    @Transactional
    public void testAdd() throws Exception {
        //given
        String url = "/screens/add";
        String startDateTime = LocalDateTime.now().toString();
        String round = "1";
        String movieId = "1";
        String theaterId = "1";

        //when
        Screen savedScreen = (Screen) this.mockMvc.perform(post(url)
                                                  .param("startDateTime", startDateTime)
                                                  .param("round", round)
                                                  .param("movieId", movieId)
                                                  .param("theaterId", theaterId)
                                                  .with(csrf()))
                                                  .andExpect(status().is3xxRedirection())
                                                  .andReturn().getFlashMap().get("savedScreen");
        //then
        assertThat(savedScreen).isNotNull();
    }
}