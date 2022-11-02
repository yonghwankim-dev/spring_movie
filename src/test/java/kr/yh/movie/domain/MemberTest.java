package kr.yh.movie.domain;

import kr.yh.movie.controller.member.MemberForm;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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
    public void testMember(){
        //given
        Member member = memberService.findById(id).get();
        MemberForm form = new MemberForm(member);
        //when
        Member createdMember = Member.member(form);
        //then
        assertThat(createdMember).isEqualTo(member);
    }

    @Test
    public void testChangeInfo(){
        //given
        Member member = memberService.findById(1L).get();
        MemberForm form = new MemberForm(member);
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