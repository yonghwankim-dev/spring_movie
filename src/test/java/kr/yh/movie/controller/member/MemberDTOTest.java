package kr.yh.movie.controller.member;

import kr.yh.movie.domain.member.Member;
import kr.yh.movie.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberDTOTest {
    @Autowired
    private MemberService memberService;

    @Test
    public void testCreateMemberDTO(){
        //given

        //when
        MemberDTO memberDTO = MemberDTO.createMemberDTO();
        //then
        assertThat(memberDTO).isNotNull();
    }

    @Test
    public void testOf(){
        //given
        Member member = memberService.findById(1L).get();
        //when
        MemberDTO memberDto = MemberDTO.of(member);
        //then
        assertThat(memberDto.getId()).isEqualTo(member.getId());
        assertThat(memberDto.getName()).isEqualTo(member.getName());
        assertThat(memberDto.getBirthday()).isEqualTo(member.getBirthday());
        assertThat(memberDto.getPhone()).isEqualTo(member.getPhone());
        assertThat(memberDto.getZipcode()).isEqualTo(member.getAddress().getZipcode());
        assertThat(memberDto.getStreet()).isEqualTo(member.getAddress().getStreet());
        assertThat(memberDto.getDetail()).isEqualTo(member.getAddress().getDetail());
        assertThat(memberDto.getEmail()).isEqualTo(member.getEmail());
        assertThat(memberDto.getUserId()).isEqualTo(member.getUserId());
        assertThat(memberDto.getPassword()).isEqualTo(member.getPassword());
        assertThat(memberDto.getGender()).isEqualTo(member.getGender());
    }

    @Test
    public void testOf_MemberIsEmpty(){
        //given
        Member member = Member.builder().build();
        //when
        MemberDTO memberDTO = MemberDTO.of(member);
        //then
        assertThat(memberDTO).isNotNull();
    }
}