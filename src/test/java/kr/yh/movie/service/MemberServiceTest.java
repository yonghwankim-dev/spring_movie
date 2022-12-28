package kr.yh.movie.service;

import kr.yh.movie.domain.Reservation;
import kr.yh.movie.domain.member.Address;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.domain.member.MemberRole;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional(readOnly = true)
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private ReservationService reservationService;

    @Test
    @Transactional
    public void deleteAll(){
        memberService.deleteAll();
    }

    @Test
    @DisplayName("회원 삭제시 회원이 예매한 예매정보 삭제를 확인")
    @Transactional
    public void givenMember_whenDeleteMember_thenDeleteReservation(){
        //given
        Long deleteId = 1L;
        //when
        memberService.deleteById(deleteId);
        Reservation reservation = reservationService.findByMemberId(deleteId).orElse(null);
        //then
        assertThat(reservation).isNull();
    }
}
