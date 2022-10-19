package kr.yh.movie.controller;

import kr.yh.movie.controller.member.MemberForm;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.vo.PageMarker;
import kr.yh.movie.vo.PageVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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
    public void testList() throws Exception {
        //given
        String url = "/members/list";

        //when
        PageMarker<Page<Member>> result = (PageMarker<Page<Member>>) this.mockMvc.perform(get(url)
                                                                         .contentType(MediaType.TEXT_HTML))
                                                                         .andExpect(status().isOk())
                                                                         .andReturn()
                                                                         .getModelAndView()
                                                                         .getModel()
                                                                         .get("result");
        //then
        assertThat(result).isNotNull();
    }

    @Test
    public void testAddForm() throws Exception{
        //given
        String url = "/members/add";
        //when
        MemberForm form = (MemberForm) this.mockMvc.perform(get(url)
                                                   .contentType(MediaType.TEXT_HTML))
                                                   .andExpect(status().isOk())
                                                   .andReturn().getModelAndView().getModel().get("form");
        //then
        assertThat(form).isNotNull();
    }

    @Test
    @Transactional
    public void testAdd_fail_duplicateUserId() throws Exception{
        //given
        String url = "/members/add";

        //when
        this.mockMvc.perform(post(url)
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
                    .param("gender", "male"))
                    .andExpect(status().is4xxClientError())
                    .andDo(print());
        //then
    }

    @Test
    @Transactional
    public void testAdd_success() throws Exception{
        //given
        String url = "/members/add";
        //when
        Member savedMember = (Member) this.mockMvc.perform(post(url)
                                                  .param("name", "김용환")
                                                  .param("birthday", "2022-09-09")
                                                  .param("phone", "010-1234-5678")
                                                  .param("zipcode", "06288")
                                                  .param("street", "서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)")
                                                  .param("detail", "")
                                                  .param("email", "user102@gmail.com")
                                                  .param("userId", "kyh9236")
                                                  .param("password", "password12345@")
                                                  .param("password_confirm", "password12345@")
                                                  .param("gender", "male")
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .with(csrf()))
                                                  .andExpect(status().is3xxRedirection())
                                                  .andReturn()
                                                  .getFlashMap()
                                                  .get("savedMember");
        //then
        assertThat(savedMember.getName()).isEqualTo("김용환");
    }


    @Test
    public void testView() throws Exception {
        //given
        //when
        //then
    }
    
    @Test
    public void 회원수정폼() throws Exception {
        //given
        String memberId = "100";
        PageVO pageVO = performMembersList();
        Member member = performMembersView(memberId, pageVO);
        //when
        MemberForm form = performMembersModify(member.getId(), pageVO);
        //then
        assertThat(form.getId()).isEqualTo(Long.parseLong(memberId));
    }

    private Member performMembersView(String memberId, PageVO pageVO) throws Exception {
        Member member = (Member) this.mockMvc.perform(get("/members/view")
                                             .sessionAttr("pageVO", pageVO)
                                             .param("id", memberId)
                                             .contentType(MediaType.TEXT_HTML))
                                             .andExpect(status().isOk())
                                             .andExpect(view().name("members/view"))
                                             .andReturn().getModelAndView().getModel().get("member");
        return member;
    }

    public PageVO performMembersList() throws Exception {
        PageVO pageVO = (PageVO) this.mockMvc.perform(get("/members/list")
                                             .contentType(MediaType.TEXT_HTML))
                                             .andExpect(status().isOk())
                                             .andReturn().getModelAndView().getModel().get("pageVO");
        return pageVO;
    }

    public MemberForm performMembersModify(Long id, PageVO pageVO) throws Exception {
        MemberForm form = (MemberForm) this.mockMvc.perform(get("/members/modify")
                                                   .sessionAttr("pageVO", pageVO)
                                                   .param("id", String.valueOf(id))
                                                   .contentType(MediaType.TEXT_HTML))
                                                   .andExpect(status().isOk())
                                                   .andExpect(view().name("members/modify"))
                                                   .andReturn().getModelAndView().getModel().get("form");
        return form;
    }

    @Test
    @Transactional
    public void 회원수정_실패() throws Exception {
        //given
        String memberId = "100";
        PageVO pageVO = performMembersList();
        Member member = performMembersView(memberId, pageVO);
        MemberForm form = performMembersModify(member.getId(), pageVO);
        form.setName("홍길동");

        //when
        this.mockMvc.perform(post("/members/modify")
                    .param("form", String.valueOf(form))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
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





}