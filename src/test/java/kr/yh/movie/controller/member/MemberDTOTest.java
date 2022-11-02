package kr.yh.movie.controller.member;

import kr.yh.movie.domain.member.Member;
import kr.yh.movie.service.MemberService;
import kr.yh.movie.util.ModelMapperUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberDTOTest {
    @Autowired
    private MemberService memberService;

    @Test
    public void testOf(){
        //given
        Member member = memberService.findById(1L).get();
        //when
        MemberDTO memberDto = MemberDTO.of(member);
        //then
        assertThat(memberDto.getId()).isEqualTo(member.getId());
    }
}