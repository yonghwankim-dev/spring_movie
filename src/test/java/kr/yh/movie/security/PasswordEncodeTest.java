package kr.yh.movie.security;

import kr.yh.movie.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PasswordEncodeTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void passwordEncode() throws Exception{
        //given
        String rawPassword = "12345678";
        //when
        String encodedPassword = passwordEncoder.encode(rawPassword);
        //then
        assertThat(rawPassword).isNotEqualTo(encodedPassword);
        assertThat(passwordEncoder.matches(rawPassword, encodedPassword)).isTrue();
    }
}