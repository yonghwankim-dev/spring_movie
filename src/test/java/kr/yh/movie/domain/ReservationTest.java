package kr.yh.movie.domain;

import kr.yh.movie.controller.screenSeat.ScreenSeatForm;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.service.MemberService;
import kr.yh.movie.service.ReservationService;
import kr.yh.movie.service.ScreenSeatService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
public class ReservationTest {
    @Autowired
    ReservationService reservationService;
    @Autowired
    MemberService memberService;
    @Autowired
    ScreenSeatService screenSeatService;

    @Test
    @DisplayName("단일 상영좌석 예매 테스트")
    @Transactional
    public void testSave() {
        //given
        Member member = memberService.findById(1L).get();
        Person person = new Person(1, 0, 0);
        ScreenSeat screenSeat = screenSeatService.findById(1945L).get();
        //when
        Reservation newReservation = Reservation.createReservation(member, person, screenSeat);
        Reservation savedReservation = reservationService.save(newReservation);
        //then
        assertThat(savedReservation).isNotNull();
        assertThat(member.getReservations().get(0)).isEqualTo(savedReservation);
    }
}