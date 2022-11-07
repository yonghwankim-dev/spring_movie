package kr.yh.movie.domain;

import kr.yh.movie.domain.member.Member;
import kr.yh.movie.service.MemberService;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
public class AddressTest {
    @Autowired
    MemberService memberService;

    @Test
    public void testToString(){
        //given
        Member member = memberService.findById(1L).get();
        //when
        String actual = member.getAddress().toString();
        //then
        assertThat(actual).isEqualTo("06035 서울 강남구 가로수길 5 ");
    }

}
