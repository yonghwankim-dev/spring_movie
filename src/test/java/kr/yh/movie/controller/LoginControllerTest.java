package kr.yh.movie.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
    public void testLoginForm() throws Exception{
        this.mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("_csrf")))
                .andDo(print());
    }
    
    @Test
    public void testLogin_fail() throws Exception{
        this.mockMvc.perform(post("/login")
                        .param("userId","user1")
                        .param("password","user1")
                        .with(csrf())
        ).andExpect(status().is3xxRedirection())
        .andDo(print());
    }
    
    @Test
    public void testLogin_success() throws Exception{
        this.mockMvc.perform(post("/login")
                        .param("userId","user1")
                        .param("password","pw1")
                        .with(csrf())
        ).andExpect(status().is3xxRedirection())
        .andDo(print());
    }

}