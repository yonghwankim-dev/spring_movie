package kr.yh.movie.controller.myPage;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MyInfoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebClient webClient;
    
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

    @Test
    public void testChangeMyInfoWithConfirmPassword_whenSubmittingForm_thenSuccess() throws Exception {
        // 페이지로 이동
        HtmlPage page = webClient.getPage("/myPage/myInfo/changeMyInfo/depth1");
        
        // 폼과 제출버튼, 텍스트 입력필드 가져옴
        HtmlForm form = page.getFormByName("form");
        HtmlButton submitBtn = page.getElementByName("submitBtn");
        HtmlInput password = page.getHtmlElementById("password");

        // 입력필드에 비밀번호 타이핑
        password.type("pw1");

        // 확인 버튼 클릭
        HtmlPage depth2Page = submitBtn.click();

        // 회원정보변경 페이지 URL 검사
//        assertThat(secondPage.getDocumentURI()).isEqualTo("/myPage/myInfo/changeMyInfo/depth2");


    }

}