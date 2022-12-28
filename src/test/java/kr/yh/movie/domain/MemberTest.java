package kr.yh.movie.domain;

import kr.yh.movie.controller.member.MemberDTO;
import kr.yh.movie.domain.member.Address;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.domain.member.MemberRole;
import kr.yh.movie.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


public class MemberTest {
    private Member fakeMember;

    @BeforeEach
    public void setup(){
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
    }

    @Test
    public void testOf(){
        //given
        // entity -> dto
        MemberDTO memberDto = MemberDTO.of(fakeMember);
        //when
        Member member = Member.of(memberDto);
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
    public void testChangeInfo(){
        //given
        Member member = fakeMember;
        MemberDTO form = MemberDTO.of(member);
        //when
        form.setName("홍길동");
        member.changeInfo(form);
        //then
        assertThat(member.getName()).isEqualTo("홍길동");
    }

    @Test
    public void testGetAuthorities(){
        //given
        Member member = fakeMember;
        //when
        List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>) member.getAuthorities();
        String authority = authorities.get(0).getAuthority();
        //then
        assertThat(authority).isEqualTo("계정별 등록할 권한");
    }
}