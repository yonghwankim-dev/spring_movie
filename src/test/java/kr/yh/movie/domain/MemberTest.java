package kr.yh.movie.domain;

import kr.yh.movie.controller.member.MemberDTO;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional(readOnly = true)
public class MemberTest {

    @Autowired
    private MemberService memberService;

    private Long id;

    @BeforeEach
    public void setup(){
        id = 1L;
    }

    @Test
    public void testOf(){
        //given
        // entity -> dto
        MemberDTO memberDto = MemberDTO.of(memberService.findById(id).get());
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
        Member member = memberService.findById(1L).get();
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
        Member member = memberService.findById(1L).get();
        //when
        List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>) member.getAuthorities();
        String authority = authorities.get(0).getAuthority();
        //then
        assertThat(authority).isEqualTo("계정별 등록할 권한");
    }
    
    @Test
    public void testGetUsername(){
        //given
        Member member = memberService.findById(id).get();
        //when
        String userId = member.getUsername();
        //then
        assertThat(userId).isEqualTo("user1");
    }
    
    @Test
    public void testIsAccountNonExpired(){
        //given
        Member member = memberService.findById(id).get();
        //when
        boolean actual = member.isAccountNonExpired();
        //then
        assertThat(actual).isTrue();
    }

    @Test
    public void testIsAccountNonLocked(){
        //given
        Member member = memberService.findById(id).get();
        //when
        boolean actual = member.isAccountNonLocked();
        //then
        assertThat(actual).isTrue();
    }

    @Test
    public void testIsCredentialsNonExpired(){
        //given
        Member member = memberService.findById(id).get();
        //when
        boolean actual = member.isCredentialsNonExpired();
        //then
        assertThat(actual).isTrue();
    }

    @Test
    public void testIsEnabled(){
        //given
        Member member = memberService.findById(id).get();
        //when
        boolean actual = member.isEnabled();
        //then
        assertThat(actual).isTrue();
    }
}