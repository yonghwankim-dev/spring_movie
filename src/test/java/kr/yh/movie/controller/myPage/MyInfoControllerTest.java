package kr.yh.movie.controller.myPage;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import kr.yh.movie.controller.cinema.CinemaController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {MyInfoController.class},
        excludeFilters = @ComponentScan.Filter(type= FilterType.REGEX, pattern = "kr.yh.movie.controller.converter.*"))
@WithMockUser
public class MyInfoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testMyInfoListPage() throws Exception {
        mockMvc.perform(get("/myPage/myInfo/myInfoList"))
                .andExpect(status().isOk())
                .andExpect(view().name("/myPage/myInfo/myInfoList"))
                .andExpect(model().attributeExists("mTab", "sTab"))
                .andExpect(model().attribute("mTab", 1))
                .andExpect(model().attribute("sTab", 0));
    }

    @Test
    public void testMyInfoListPage_whenNotLoginStatus_thenMovingLoginPage() throws Exception {
        mockMvc.perform(get("/myPage/myInfo/myInfoList"));
    }

    @Test
    public void testChangeMyInfoWithConfirmPasswordPage() throws Exception {
        mockMvc.perform(get("/myPage/myInfo/changeMyInfo/depth1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/myPage/myInfo/changeMyInfo/depth1"))
                .andExpect(model().attributeExists("form"));
    }
}