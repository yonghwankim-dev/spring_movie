package kr.yh.movie.domain;

import kr.yh.movie.controller.screenSeat.ScreenSeatForm;
import kr.yh.movie.domain.member.Address;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.domain.member.MemberRole;
import kr.yh.movie.service.MemberService;
import kr.yh.movie.service.ReservationService;
import kr.yh.movie.service.ScreenSeatService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {
    private Member member;
    private Cinema cinema;
    private Theater theater;
    private Screen screen;
    private Movie movie;
    private ScreenSeat screenSeat;

    private Reservation reservation;

    @BeforeEach
    public void setup(){
        member = Member.builder().id(1L)
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

        cinema = Cinema.builder()
                        .id(1L)
                        .name("가산디지털")
                        .location("서울")
                        .build();

        theater = Theater.builder()
                         .id(1L)
                         .name("1관")
                         .build();

        screen = Screen.builder()
                        .id(1L)
                        .startDateTime(LocalDateTime.now())
                        .round(1)
                        .build();

        movie = Movie.builder()
                    .id(1L)
                    .name("올빼미")
                    .filmRating(15)
                    .runtime(120)
                    .build();

        screenSeat = ScreenSeat.builder()
                                .id(1L)
                                .status(ScreenSeatStatus.EMPTY)
                                .build();
        reservation = Reservation.builder()
                                .id(1L)
                                .reservedPrice(12000)
                                .status(ReservationStatus.RESERVED)
                                .screenSeats(new ArrayList<>(List.of(screenSeat)))
                                .member(member)
                                .person(new Person(1, 0, 0))
                                .reservedDateTime(LocalDateTime.now())
                                .build();

        reservation.setMember(member);
    }

    @Test
    @DisplayName("예매에 멤버가 설정되어 정보가 일치하는지 테스트")
    public void testSetMember(){
        //given
        Member otherMember = Member.builder()
                                    .id(2L)
                                    .name("홍길동")
                                    .build();
        //when
        reservation.setMember(otherMember);
        //then
        assertThat(reservation.getMember()).isEqualTo(otherMember);
        assertThat(member.getReservations().contains(reservation)).isFalse();
        assertThat(otherMember.getReservations().contains(reservation)).isTrue();
    }

    @Test
    @DisplayName("예매 도메인이 주어질때 상영좌석을 설정하고 정보가 일치하는지 테스트")
    public void testSetScreenSeats(){
        //given

        //when
        reservation.setScreenSeats(new ScreenSeat[]{screenSeat});
        //then
        assertThat(reservation.getScreenSeats().contains(screenSeat)).isTrue();
        assertThat(screenSeat.getReservation()).isEqualTo(reservation);
    }

}