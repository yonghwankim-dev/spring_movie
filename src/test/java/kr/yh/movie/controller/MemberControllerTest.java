package kr.yh.movie.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional(readOnly = true)
public class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;

    // 회원가입 폼페이지에 _csrf 응답이 포함되어 있어야하는 테스트
    @Test
    public void 회원가입폼() throws Exception{
        this.mockMvc.perform(get("/members/new"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("_csrf")))
                .andDo(print());
    }

    // csrf 토큰없이 회원가입 시도시 회원가입 실패를 예상
    @Test
    @Transactional
    public void 회원가입_실패() throws Exception{
        this.mockMvc.perform(post("/members/new")
                .param("name","김용환")
                .param("birthday","2022-09-09")
                .param("phone","010-1234-5678")
                .param("zipcode", "06288")
                .param("street","서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)")
                .param("detail","")
                .param("email","user1@gmail.com")
                .param("userId","user1")
                .param("password","password12345@")
                .param("password_confirm","password12345@")
                .param("gender", "male")
        ).andExpect(status().is4xxClientError())
         .andDo(print());
    }
    
    @Test
    @Transactional
    public void 회원가입_성공() throws Exception{
        this.mockMvc.perform(post("/members/new")
                .param("name","김용환")
                .param("birthday","2022-09-09")
                .param("phone","010-1234-5678")
                .param("zipcode", "06288")
                .param("street","서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)")
                .param("detail","")
                .param("email","user101@gmail.com")
                .param("userId","user101")
                .param("password","password12345@")
                .param("password_confirm","password12345@")
                .param("gender", "male")
                .with(csrf())
        ).andExpect(status().is3xxRedirection())
         .andDo(print());
    }


}