package kr.yh.movie.controller;

import kr.yh.movie.controller.login.LoginController;
import kr.yh.movie.controller.ticket.TicketController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LoginController.class,
        excludeFilters = @ComponentScan.Filter(type= FilterType.REGEX, pattern = "kr.yh.movie.controller.converter.*"))

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
    public void testLoginForm_errorAndExceptionIsEmpty() throws Exception {
        //given
        String error = "";
        String exception = "";
        //when
        this.mockMvc.perform(get("/login")
                    .param("error", error)
                    .param("exception", exception)
                    .contentType(TEXT_HTML))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("_csrf")))
                    .andDo(print());
        //then
    }

    @Test
    public void testLogin_fail_UserIdOrPasswordIsIncorrect() throws Exception{
        //given
        String userId = "seiofjawoeijfioawef";
        String incorrectPassword = "awoiejfoawiejfio";
        //when
        String redirectedUrl = this.mockMvc.perform(post("/login")
                        .param("userId", userId)
                        .param("password", incorrectPassword)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse().getRedirectedUrl();
        String decode = URLDecoder.decode(redirectedUrl, "UTF-8");
        assertThat(decode).contains("/login?error");
    }

    @Test
    public void testLogin_fail_passwordIsIncorrect() throws Exception{
        //given
        String userId = "user1";
        String incorrectPassword = "user1";
        //when
        String redirectedUrl = this.mockMvc.perform(post("/login")
                                            .param("userId", userId)
                                            .param("password", incorrectPassword)
                                            .with(csrf()))
                                            .andExpect(status().is3xxRedirection())
                                            .andReturn().getResponse().getRedirectedUrl();
        String decode = URLDecoder.decode(redirectedUrl, "UTF-8");
        assertThat(decode).contains("/login?error");
    }
    
    @Test
    public void testLogin_success() throws Exception{
        //given
        String userId = "user1";
        String password = "pw1";
        //when
        this.mockMvc.perform(post("/login")
                    .param("userId",userId)
                    .param("password",password)
                    .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
        //then
    }


}