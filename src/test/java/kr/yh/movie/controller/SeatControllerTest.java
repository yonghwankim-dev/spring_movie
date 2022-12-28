package kr.yh.movie.controller;

import com.querydsl.core.BooleanBuilder;
import kr.yh.movie.controller.cinema.CinemaController;
import kr.yh.movie.controller.seat.SeatController;
import kr.yh.movie.controller.seat.SeatForm;
import kr.yh.movie.domain.*;
import kr.yh.movie.repository.SeatRepository;
import kr.yh.movie.service.SeatService;
import kr.yh.movie.service.TheaterService;
import kr.yh.movie.vo.PageMarker;
import kr.yh.movie.vo.PageVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = {SeatController.class},
        excludeFilters = @ComponentScan.Filter(type= FilterType.REGEX, pattern = "kr.yh.movie.controller.converter.*"))
@WithMockUser
public class SeatControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    SeatService seatService;

    @MockBean
    SeatRepository seatRepository;

    @MockBean
    TheaterService theaterService;

    Cinema fakeCinema;
    Theater fakeTheater;
    Seat fakeSeat;

    @BeforeEach
    public void setup() {
        fakeCinema = Cinema.builder().id(1L).name("가산디지털").location("서울").build();
        fakeTheater = Theater.builder().id(1L).name("2관").cinema(fakeCinema).build();
        fakeSeat = Seat.builder().id(1L).seat_row("A").seat_col("1").theater(fakeTheater).build();
    }

    @Test
    @DisplayName("상영관 아이디번호가 주어졌을때 좌석 목록들과 페이지가 이동되는지 테스트")
    public void testList() throws Exception {
        //given
        String url = "/seats/list";
        String theaterId = "1";
        PageVO pageVO = new PageVO();
        Pageable page = pageVO.makePageable(0, "id");
        BooleanBuilder fakeBuilder = new BooleanBuilder();
        fakeBuilder.and(QSeat.seat.id.gt(0));
        Page<Seat> fakePage = new PageImpl<>(new ArrayList<>(), page, 10);
        //mocking
        when(seatService.makePredicates(pageVO.getType(), pageVO.getKeyword(), Long.valueOf(theaterId))).thenReturn(fakeBuilder);
        when(seatService.findAll(fakeBuilder, page)).thenReturn(fakePage);
        //when
        this.mockMvc.perform(get(url)
                     .param("theaterId", theaterId)
                     .contentType(TEXT_HTML))
                     .andExpect(status().isOk())
                     .andExpect(model().attributeExists("result"));
        //then
    }

    @Test
    public void testAddForm() throws Exception {
        //given
        String url = "/seats/add";
        String theaterId = "1";
        //when
        this.mockMvc.perform(get(url)
                   .param("theaterId", theaterId)
                   .contentType(TEXT_HTML))
                   .andExpect(status().isOk())
                   .andExpect(model().attributeExists("form"));
        //then
    }

    @Test
    public void testAdd() throws Exception {
        //given
        String url = "/seats/add";
        String seat_row = "A";
        String seat_col = "1";
        String theaterId = "1";

        Cinema fakeCinema = Cinema.builder().id(1L).name("가산디지털").location("서울").build();
        Theater fakeTheater = Theater.builder().id(1L).name("2관").cinema(fakeCinema).build();
        Seat fakeSeat = Seat.builder().id(1L).seat_row("A").seat_col("1").theater(fakeTheater).build();
        //mocking
        when(theaterService.findById(Long.valueOf(theaterId))).thenReturn(Optional.ofNullable(fakeTheater));
        when(seatService.save(fakeSeat)).thenReturn(fakeSeat);

        //when
        this.mockMvc.perform(post(url)
                    .param("seat_row", seat_row)
                    .param("seat_col", seat_col)
                    .param("theaterId", theaterId)
                    .contentType(APPLICATION_JSON)
                    .with(csrf()))
                    .andExpect(status().is3xxRedirection());
        //then
    }

    @Test
    public void testView() throws Exception {
        //given
        String url = "/seats/view";
        String seatId = "1";
        //mocking
        when(seatService.findById(Long.valueOf(seatId))).thenReturn(Optional.ofNullable(fakeSeat));

        //when
        this.mockMvc.perform(get(url)
                   .param("seatId", seatId)
                   .contentType(TEXT_HTML))
                   .andExpect(status().isOk())
                   .andExpect(model().attributeExists("vo"));
        //then
    }



    @Test
    public void testModifyForm() throws Exception {
        //given
        String url = "/seats/modify";
        String seatId = "1";
        //mocking
        when(seatService.findById(Long.valueOf(seatId))).thenReturn(Optional.ofNullable(fakeSeat));
        //when
        this.mockMvc.perform(get(url)
                   .param("seatId", seatId)
                   .contentType(TEXT_HTML))
                   .andExpect(status().isOk())
                   .andExpect(model().attributeExists("form"));
        //then
    }

    @Test
    public void testModify() throws Exception {
        //given
        String url = "/seats/modify";
        String seatId = "1";
        String seat_row = "B";
        String seat_col = "1";
        String theaterId = "1";
        //mocking
        when(theaterService.findById(Long.valueOf(theaterId))).thenReturn(Optional.ofNullable(fakeTheater));
        when(seatService.findById(Long.valueOf(seatId))).thenReturn(Optional.ofNullable(fakeSeat));
        when(seatService.save(fakeSeat)).thenReturn(fakeSeat);
        //when
        Seat modifiedSeat = (Seat) this.mockMvc.perform(post(url)
                                               .param("id", seatId)
                                               .param("seat_row", seat_row)
                                               .param("seat_col", seat_col)
                                               .param("theaterId", theaterId)
                                               .contentType(APPLICATION_JSON)
                                               .with(csrf()))
                                               .andExpect(status().is3xxRedirection())
                                               .andReturn().getFlashMap().get("modifiedSeat");
        //then
        assertThat(modifiedSeat.getSeat_row()).isEqualTo(seat_row);
    }

    @Test
    public void testDelete() throws Exception {
        //given
        String url = "/seats/delete";
        String theaterId = "1";
        String seatId = "1";
        //when
        String msg = (String) this.mockMvc.perform(post(url)
                                          .param("theaterId", theaterId)
                                          .param("id", seatId)
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
        String url = "/seats/deletes";
        String theaterId = "1";
        String[] seatIds = {"1"};
        //when
        String msg = (String) this.mockMvc.perform(post(url)
                                          .param("theaterId", theaterId)
                                          .param("checks", seatIds)
                                          .contentType(APPLICATION_JSON)
                                          .with(csrf()))
                                          .andExpect(status().is3xxRedirection())
                                          .andReturn().getFlashMap().get("msg");
        //then
        assertThat(msg).isEqualTo("success");
    }
}
