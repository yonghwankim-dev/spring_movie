package kr.yh.movie.controller;

import kr.yh.movie.controller.screen.ScreenForm;
import kr.yh.movie.domain.Movie;
import kr.yh.movie.domain.Screen;
import kr.yh.movie.domain.Theater;
import kr.yh.movie.service.ScreenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional(readOnly = true)
public class ScreenControllerTest {
    @Autowired
    ScreenService screenService;

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
    
    @Test
    public void testView() throws Exception {
        //given
        String url = "/screens/view";
        String screenId = "3";
        //when
        Screen screen = (Screen) this.mockMvc.perform(get(url)
                                             .param("screenId", screenId)
                                             .contentType(MediaType.TEXT_HTML))
                                             .andExpect(status().isOk())
                                             .andReturn().getModelAndView().getModel().get("vo");
        //then
        assertThat(screen.getId()).isEqualTo(3L);
    }

    @Test
    public void testModifyForm() throws Exception {
        //given
        String url = "/screens/modify";
        String screenId = "3";
        String cinemaId = "1";
        //when
        Map<String, Object> model = this.mockMvc.perform(get(url)
                                                .param("screenId", screenId)
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
    public void testModify() throws Exception {
        //given
        String url = "/screens/modify";
        String screenId = "3";
        String movieId = "1";
        String theaterId = "1";
        String round = "2";
        String startDateTime = LocalDateTime.now().toString();

        //when
        String modifiedScreenId = (String) this.mockMvc.perform(post(url)
                                                      .param("id", screenId)
                                                      .param("startDateTime", startDateTime)
                                                      .param("round", round)
                                                      .param("movieId", movieId)
                                                      .param("theaterId", theaterId)
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .with(csrf()))
                                                      .andExpect(status().is3xxRedirection())
                                                      .andReturn()
                                                      .getModelAndView().getModel().get("screenId");
        Screen foundScreen = screenService.findById(Long.parseLong(modifiedScreenId)).get();
        //then
        assertThat(foundScreen.getId()).isEqualTo(Long.parseLong(screenId));
        assertThat(foundScreen.getRound()).isEqualTo(Integer.parseInt(round));
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        //given
        String url = "/screens/delete";
        String screenId = "3";

        //when
        String msg = (String) this.mockMvc.perform(post(url)
                                          .param("screenId", screenId)
                                          .contentType(MediaType.APPLICATION_JSON)
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
        String url = "/screens/deletes";
        String[] screenIds = {"3"};
        //when
        String msg = (String) this.mockMvc.perform(post(url)
                                          .param("checks", screenIds)
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .with(csrf()))
                                          .andExpect(status().is3xxRedirection())
                                          .andReturn().getFlashMap().get("msg");
        //then
        assertThat(msg).isEqualTo("success");
    }


}