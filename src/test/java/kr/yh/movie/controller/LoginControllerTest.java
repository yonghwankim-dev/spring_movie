package kr.yh.movie.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional(readOnly = true)
public class LoginControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void 로그인폼() throws Exception{
        this.mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("_csrf")))
                .andDo(print());
    }
    
    @Test
    public void 로그인_실패() throws Exception{
        this.mockMvc.perform(post("/login")
                        .param("userId","user1")
                        .param("password","user1")
        ).andExpect(status().is4xxClientError())
        .andDo(print());
    }
    
    @Test
    public void 로그인_성공() throws Exception{
        this.mockMvc.perform(post("/login")
                        .param("userId","user1")
                        .param("password","pw1")
        ).andExpect(status().is4xxClientError())
        .andDo(print());
    }

}