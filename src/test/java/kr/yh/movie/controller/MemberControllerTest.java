package kr.yh.movie.controller;

import kr.yh.movie.domain.member.Member;
import kr.yh.movie.vo.PageVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.FlashMap;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional(readOnly = true)
public class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void 회원목록() throws Exception {
        //given

        //when
        this.mockMvc.perform(get("/members/list")
                    .contentType(MediaType.TEXT_HTML))
                    .andExpect(status().isOk())
                    .andDo(print());
        //then
    }


    @Test
    public void 회원상세정보() throws Exception {
        //given
        PageVO pageVO = (PageVO) this.mockMvc.perform(get("/members/list")
                                             .contentType(MediaType.TEXT_HTML))
                                             .andExpect(status().isOk())
                                             .andReturn().getModelAndView().getModel().get("pageVO");

        //when
        Member member = (Member) this.mockMvc.perform(get("/members/view")
                                       .sessionAttr("pageVO", pageVO)
                                       .param("id", "100")
                                       .contentType(MediaType.TEXT_HTML))
                                       .andExpect(status().isOk())
                                       .andExpect(view().name("members/view"))
                                       .andReturn().getModelAndView().getModel().get("member");
        //then
        assertThat(member.getId()).isEqualTo(100);
    }
    
    @Test
    public void 회원수정폼() throws Exception {
        //given
        String memberId = "100";
        PageVO pageVO = (PageVO) this.mockMvc.perform(get("/members/list")
                                             .contentType(MediaType.TEXT_HTML))
                                             .andExpect(status().isOk())
                                             .andReturn().getModelAndView().getModel().get("pageVO");

        Member member = (Member) this.mockMvc.perform(get("/members/view")
                                             .sessionAttr("pageVO", pageVO)
                                             .param("id", memberId)
                                             .contentType(MediaType.TEXT_HTML))
                                             .andExpect(status().isOk())
                                             .andExpect(view().name("members/view"))
                                             .andReturn().getModelAndView().getModel().get("member");


        //when
        MemberForm form = (MemberForm) this.mockMvc.perform(get("/members/modify")
                                                   .sessionAttr("pageVO", pageVO)
                                                   .param("id", String.valueOf(member.getId()))
                                                   .contentType(MediaType.TEXT_HTML))
                                                   .andExpect(status().isOk())
                                                   .andExpect(view().name("members/modify"))
                                                   .andReturn().getModelAndView().getModel().get("form");
        //then
        assertThat(form.getId()).isEqualTo(Long.parseLong(memberId));
    }
    
    @Test
    @Transactional
    public void 회원수정_실패(){
        //given
            
        //when
        
        //then
    }
    
    @Test
    @Transactional
    public void 회원수정_성공(){
        //given
        
        //when
        
        //then
    }
    
    @Test
    @Transactional
    public void 회원삭제_실패(){
        //given
        
        //when
        
        //then
    }
    
    @Test
    @Transactional
    public void 회원삭제_성공(){
        //given
        
        //when
        
        //then
    }
    
    @Test
    @Transactional
    public void 회원다수삭제_실패(){
        //given
        
        //when
        
        //then
    }

    @Test
    @Transactional
    public void 회원다수삭제_성공(){
        //given
        
        //when
        
        //then
    }

    @Test
    public void 회원가입폼() throws Exception{
        this.mockMvc.perform(get("/members/add"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("_csrf")))
                    .andDo(print());
    }

    @Test
    @Transactional
    public void 회원가입_실패() throws Exception{
        this.mockMvc.perform(post("/members/add")
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
        this.mockMvc.perform(post("/members/add")
                .param("name","김용환")
                .param("birthday","2022-09-09")
                .param("phone","010-1234-5678")
                .param("zipcode", "06288")
                .param("street","서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)")
                .param("detail","")
                .param("email","user102@gmail.com")
                .param("userId","kyh9236")
                .param("password","password12345@")
                .param("password_confirm","password12345@")
                .param("gender", "male")
                .with(csrf())
        ).andExpect(status().is3xxRedirection())
         .andDo(print());
    }



}