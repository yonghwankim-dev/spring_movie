package kr.yh.movie.controller;

import com.querydsl.core.BooleanBuilder;
import kr.yh.movie.controller.cinema.CinemaController;
import kr.yh.movie.controller.reservation.ReservationController;
import kr.yh.movie.domain.*;
import kr.yh.movie.domain.member.Address;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.domain.member.MemberRole;
import kr.yh.movie.service.ReservationService;
import kr.yh.movie.vo.PageMarker;
import kr.yh.movie.vo.PageVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static kr.yh.movie.domain.ReservationStatus.*;
import static kr.yh.movie.domain.ScreenSeatStatus.*;
import static kr.yh.movie.domain.ScreenSeatStatus.RESERVED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ReservationController.class},
        excludeFilters = @ComponentScan.Filter(type= FilterType.REGEX, pattern = "kr.yh.movie.controller.converter.*"))
@WithMockUser
public class ReservationControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReservationService reservationService;

    private String cinemaId;
    private Member fakeMember;
    private Reservation fakeReservation;
    @BeforeEach
    public void setup(){
        cinemaId = "1";
        fakeMember = Member.builder()
                            .id(1L)
                            .address(new Address("06228", "서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)", ""))
                            .birthday(LocalDate.of(1995, 01, 01))
                            .email("user1@gmail.com")
                            .gender("male")
                            .name("김용환")
                            .userId("user1")
                            .password("user1")
                            .phone("010-1234-4567")
                            .roleName(MemberRole.USER)
                            .regDate(LocalDateTime.now())
                            .build();

        fakeReservation = Reservation.builder()
                                    .id(1L)
                                    .reservedDateTime(LocalDateTime.now())
                                    .person(new Person(1, 0, 0))
                                    .reservedPrice(12000)
                                    .status(ReservationStatus.RESERVED)
                                    .member(fakeMember)
                                    .build();
    }

    @Test
    @DisplayName("예매 리스트 페이지로 이동되는지 테스트")
    public void testList() throws Exception {
        //given
        String url = "/reservations/list";
        PageVO pageVO = new PageVO();
        Pageable page = pageVO.makePageable(0, "id");
        BooleanBuilder fakeBuilder = new BooleanBuilder();
        fakeBuilder.and(QReservation.reservation.id.gt(0));
        Page<Reservation> fakePage = new PageImpl<>(new ArrayList<>(), page, 10);
        //mocking
        when(reservationService.makePredicates(
                    pageVO.getType(), pageVO.getKeyword(), Long.valueOf(cinemaId)))
                .thenReturn(fakeBuilder);
        when(reservationService.findAll(fakeBuilder, page)).thenReturn(fakePage);

        //when
        this.mockMvc.perform(get(url)
                    .sessionAttr("cinemaId", cinemaId))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("result"));
        //then
    }

    @Test
    public void testView() throws Exception {
        //given
        String url = "/reservations/view";
        String reservationId = "1";

        //mocking
        when(reservationService.findById(Long.valueOf(reservationId))).thenReturn(Optional.ofNullable(fakeReservation));
        //when
        this.mockMvc.perform(get(url)
                    .param("reservationId", reservationId)
                    .contentType(TEXT_HTML))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("vo"));
        //then
    }
}
