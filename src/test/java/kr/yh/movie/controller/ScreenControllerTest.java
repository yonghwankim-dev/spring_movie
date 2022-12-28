package kr.yh.movie.controller;

import com.querydsl.core.BooleanBuilder;
import kr.yh.movie.controller.cinema.CinemaController;
import kr.yh.movie.controller.screen.ScreenController;
import kr.yh.movie.controller.screen.ScreenForm;
import kr.yh.movie.domain.*;
import kr.yh.movie.service.MovieService;
import kr.yh.movie.service.ScreenService;
import kr.yh.movie.service.TheaterService;
import kr.yh.movie.vo.PageVO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {ScreenController.class},
        excludeFilters = @ComponentScan.Filter(type= FilterType.REGEX, pattern = "kr.yh.movie.controller.converter.*"))
@WithMockUser
public class ScreenControllerTest {
    @MockBean
    ScreenService screenService;

    @MockBean
    MovieService movieService;

    @MockBean
    TheaterService theaterService;

    @Autowired
    MockMvc mockMvc;

    private String screenId;
    private Cinema fakeCinema;
    private Movie fakeMovie;
    private Theater fakeTheater;
    private Screen fakeScreen;

    @BeforeEach
    public void setup(){
        screenId = String.valueOf(1L);
        fakeCinema = Cinema.builder().id(1L).name("가산디지털").location("서울").build();
        fakeMovie = Movie.builder().id(1L).name("올빼미").filmRating(15).runtime(120).build();
        fakeTheater = Theater.builder().id(1L).name("1관").cinema(fakeCinema).build();
        fakeScreen = Screen.builder().movie(fakeMovie).round(1).theater(fakeTheater).startDateTime(LocalDateTime.now()).build();
    }

    @Test
    public void testList() throws Exception {
        //given
        String url = "/screens/list";
        PageVO pageVO = new PageVO();
        Pageable page = pageVO.makePageable(0, "id");
        BooleanBuilder fakeBuilder = new BooleanBuilder();
        fakeBuilder.and(QScreen.screen.id.gt(0));
        Page<Screen> fakePage = new PageImpl<>(new ArrayList<>(), page, 10);
        //mocking
        when(screenService.makePredicates(pageVO.getType(), pageVO.getKeyword())).thenReturn(fakeBuilder);
        when(screenService.findAll(fakeBuilder, page)).thenReturn(fakePage);
        //when
        Objects.requireNonNull(this.mockMvc.perform(get(url)
                .contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("result")));
        //then
    }
    
    @Test
    public void testAddForm() throws Exception {
        //given
        String url = "/screens/add";
        String cinemaId = "1";
        //when
        this.mockMvc.perform(get(url)
                    .sessionAttr("cinemaId", cinemaId)
                    .contentType(MediaType.TEXT_HTML))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("form"))
                    .andExpect(model().attributeExists("movies"))
                    .andExpect(model().attributeExists("theaters"));
    }
    
    @Test
    public void testAdd() throws Exception {
        //given
        String url = "/screens/add";
        String startDateTime = LocalDateTime.now().toString();
        String round = "1";
        String movieId = "1";
        String theaterId = "1";
        Cinema fakeCinema = Cinema.builder().id(1L).name("가산디지털").location("서울").build();
        Movie fakeMovie = Movie.builder().id(Long.valueOf(movieId)).name("올빼미").filmRating(15).runtime(120).build();
        Theater fakeTheater = Theater.builder().id(Long.valueOf(theaterId)).name("1관").cinema(fakeCinema).build();
        Screen fakeScreen = Screen.builder().movie(fakeMovie).round(1).theater(fakeTheater).startDateTime(LocalDateTime.parse(startDateTime)).build();
        //mocking
        when(movieService.findById(Long.valueOf(movieId))).thenReturn(Optional.ofNullable(fakeMovie));
        when(theaterService.findById(Long.valueOf(theaterId))).thenReturn(Optional.ofNullable(fakeTheater));
        when(screenService.save(fakeScreen)).thenReturn(fakeScreen);
        //when
        this.mockMvc.perform(post(url)
                    .param("startDateTime", startDateTime)
                    .param("round", round)
                    .param("movieId", movieId)
                    .param("theaterId", theaterId)
                    .with(csrf()))
                    .andExpect(status().is3xxRedirection());
        //then
    }
    
    @Test
    public void testView() throws Exception {
        //given
        String url = "/screens/view";

        //mocking
        when(screenService.findById(Long.valueOf(screenId))).thenReturn(Optional.ofNullable(fakeScreen));
        //when
        this.mockMvc.perform(get(url)
                     .param("screenId", screenId)
                     .contentType(MediaType.TEXT_HTML))
                     .andExpect(status().isOk())
                     .andExpect(model().attributeExists("vo"));
        //then
    }

    @Test
    public void testModifyForm() throws Exception {
        //given
        String url = "/screens/modify";
        String cinemaId = "1";
        //mokcing
        when(movieService.findAll()).thenReturn(new ArrayList<>());
        when(theaterService.findAllByCinemaId(Long.valueOf(cinemaId))).thenReturn(new ArrayList<>());
        when(screenService.findById(Long.valueOf(screenId))).thenReturn(Optional.ofNullable(fakeScreen));
        //when
        this.mockMvc.perform(get(url)
                    .param("screenId", screenId)
                    .sessionAttr("cinemaId", cinemaId)
                    .contentType(MediaType.TEXT_HTML))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("form"))
                    .andExpect(model().attributeExists("movies"))
                    .andExpect(model().attributeExists("theaters"));
        //then
    }
    
    @Test
    public void testModify() throws Exception {
        //given
        String url = "/screens/modify";
        String movieId = "1";
        String theaterId = "1";
        String round = "2";
        String startDateTime = LocalDateTime.now().toString();
        //mocking
        when(movieService.findById(Long.valueOf(movieId))).thenReturn(Optional.ofNullable(fakeMovie));
        when(theaterService.findById(Long.valueOf(theaterId))).thenReturn(Optional.ofNullable(fakeTheater));
        when(screenService.findById(fakeScreen.getId())).thenReturn(Optional.ofNullable(fakeScreen));
        when(screenService.save(fakeScreen)).thenReturn(fakeScreen);
        //when
        this.mockMvc.perform(post(url)
                    .param("id", screenId)
                    .param("startDateTime", startDateTime)
                    .param("round", round)
                    .param("movieId", movieId)
                    .param("theaterId", theaterId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/screens/view"));
        //then
    }

    @Test
    public void testDelete() throws Exception {
        //given
        String url = "/screens/delete";
        //when
        this.mockMvc.perform(post(url)
                    .param("id", screenId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                    .andExpect(status().is3xxRedirection());
        //then
    }

    @Test
    public void testDeletes() throws Exception {
        //given
        String url = "/screens/deletes";
        String[] screenIds = {screenId};
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