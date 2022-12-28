package kr.yh.movie.controller.member;

import kr.yh.movie.controller.cinema.CinemaController;
import kr.yh.movie.domain.member.Address;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberDTOTest {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private Member fakeMember;

    @BeforeEach
    public void setup(){
        fakeMember = Member.builder()
                            .id(1L)
                            .birthday(LocalDate.of(1990,1,1))
                            .phone("010-1234-5678")
                            .address(new Address("06288", "서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)", ""))
                            .email("fakeuser@gmail.com")
                            .userId("fakeuser")
                            .password(passwordEncoder.encode("fakeuser1234@"))
                            .gender("male")
                            .name("김용환")
                            .build();
    }

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
        Member member = fakeMember;
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