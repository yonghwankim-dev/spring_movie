package kr.yh.movie.controller;

import com.querydsl.core.BooleanBuilder;
import kr.yh.movie.controller.cinema.CinemaController;
import kr.yh.movie.controller.cinema.CinemaDTO;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.QCinema;
import kr.yh.movie.service.CinemaService;
import kr.yh.movie.vo.PageVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {CinemaController.class},
        excludeFilters = @ComponentScan.Filter(type= FilterType.REGEX, pattern = "kr.yh.movie.controller.converter.*"))
@WithMockUser
public class CinemaControllerTest {
    @MockBean
    CinemaService cinemaService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testList() throws Exception {
        //given
        String url = "/cinemas/list";
        PageVO pageVO = new PageVO();
        Pageable page = pageVO.makePageable(0, "id");
        BooleanBuilder fakeBuilder = new BooleanBuilder();
        fakeBuilder.and(QCinema.cinema.id.gt(0));
        Page<Cinema> fakePage = new PageImpl<>(new ArrayList<>(), page, 10);
        //mocking
        when(cinemaService.makePredicates(pageVO.getType(), pageVO.getKeyword())).thenReturn(fakeBuilder);
        when(cinemaService.findAll(fakeBuilder, page)).thenReturn(fakePage);
        //when
        this.mockMvc.perform(get(url)
                    .contentType(TEXT_HTML))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("result"));
        //then
    }
    
    @Test
    public void testAddForm() throws Exception {
        //given
        String url = "/cinemas/add";
        //when
        CinemaDTO form = (CinemaDTO) Objects.requireNonNull(this.mockMvc.perform(get(url)
                                                                        .contentType(TEXT_HTML))
                                                                        .andExpect(status().isOk())
                                                                        .andReturn()
                                                                        .getModelAndView())
                                                                        .getModel()
                                                                        .get("form");
        //then
        assertThat(form).isNotNull();
    }
    
    @Test
    public void testAdd() throws Exception {
        //given
        String url = "/cinemas/add";
        String name = "강남";
        String loc  = "서울";
        Cinema fakeCinema = Cinema.builder().name(name).location(loc).build();
        //mocking
        when(cinemaService.save(fakeCinema)).thenReturn(fakeCinema);

        //when
        Cinema savedCinema = (Cinema) this.mockMvc.perform(post(url)
                                                  .param("name", name)
                                                  .param("loc", loc)
                                                  .contentType(APPLICATION_JSON)
                                                  .with(csrf()))
                                                  .andExpect(status().is3xxRedirection())
                                                  .andReturn().getFlashMap().get("savedCinema");
        //then
        assertThat(savedCinema).isEqualTo(fakeCinema);
    }
    
    @Test
    public void testView() throws Exception {
        //given
        String url = "/cinemas/view";
        String cinemaId = "1";
        Cinema fakeCinema = Cinema.builder().id(Long.valueOf(cinemaId)).name("가산디지털").location("서울").build();
        //mocking
        when(cinemaService.findById(Long.valueOf(cinemaId))).thenReturn(Optional.ofNullable(fakeCinema));

        //when
        this.mockMvc.perform(get(url)
                    .param("cinemaId", cinemaId)
                    .contentType(TEXT_HTML))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("vo"));
        //then
    }
    
    @Test
    public void testModifyForm() throws Exception {
        //given
        String url = "/cinemas/modify";
        String cinemaId = "1";
        Cinema fakeCinema = Cinema.builder().id(Long.valueOf(cinemaId)).name("가산디지털").location("서울").build();
        //mocking
        when(cinemaService.findById(Long.valueOf(cinemaId))).thenReturn(Optional.ofNullable(fakeCinema));
        //when
        CinemaDTO form = (CinemaDTO) Objects.requireNonNull(this.mockMvc.perform(get(url)
                                                                        .param("cinemaId", cinemaId)
                                                                        .contentType(APPLICATION_JSON))
                                                                        .andExpect(status().isOk())
                                                                        .andReturn().getModelAndView())
                                                                        .getModel().get("form");
        //then
        assertThat(form.getId()).isEqualTo(Long.parseLong(cinemaId));
    }
    
    @Test
    public void testModify() throws Exception {
        //given
        String url = "/cinemas/modify";
        String cinemaId = "1";
        String modifiedName = "강서";
        String loc = "서울";
        Cinema fakeCinema = Cinema.builder().id(Long.valueOf(cinemaId)).name("가산디지털").location("서울").build();
        //mocking
        when(cinemaService.findById(Long.valueOf(cinemaId))).thenReturn(Optional.ofNullable(fakeCinema));
        when(cinemaService.save(fakeCinema)).thenReturn(fakeCinema);
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
        Cinema fakeCinema = Cinema.builder().id(Long.valueOf(cinemaId)).name("가산디지털").location("서울").build();
        //mocking
        when(cinemaService.findById(Long.valueOf(cinemaId))).thenReturn(Optional.ofNullable(fakeCinema));

        //when
        this.mockMvc.perform(get(url)
                    .param("cinemaId", cinemaId)
                    .contentType(TEXT_HTML))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("vo"));
        //then
    }
}
