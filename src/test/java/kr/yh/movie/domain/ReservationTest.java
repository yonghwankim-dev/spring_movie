package kr.yh.movie.domain;

import kr.yh.movie.domain.member.Member;
import kr.yh.movie.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReservationTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void createReservation(){
        //given
        Member member = memberService.findById(1L).get();
        Person person = new Person(0,2,0);

        //when
//        Reservation.createReservation(member, person, )
        //then
    }
}