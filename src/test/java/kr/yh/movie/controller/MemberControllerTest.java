package kr.yh.movie.controller;

import kr.yh.movie.controller.member.MemberDTO;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.vo.PageMarker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
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
        MemberDTO form = (MemberDTO) this.mockMvc.perform(get(url)
                                                   .contentType(MediaType.TEXT_HTML))
                                                   .andExpect(status().isOk())
                                                   .andReturn().getModelAndView().getModel().get("form");
        //then
        assertThat(form).isNotNull();
    }

    @Test
    @Transactional
    public void testAdd_success() throws Exception{
        //given
        String url = "/members/add";
        //when
        MemberDTO savedMember = (MemberDTO) this.mockMvc.perform(post(url)
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
    @Transactional
    public void testAdd_fail_nameIsEmpty() throws Exception{
        //given
        String url = "/members/add";
        //when
        ResultActions resultActions = this.mockMvc.perform(post(url)
                                                .param("name", "")
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
                                                .andExpect(status().is2xxSuccessful());
        String actual = resultActions.andReturn().getModelAndView().getModel().get("valid_name").toString();
        //then
        assertThat(actual).isEqualTo("한글 또는 영문을 입력해주세요");
    }

    @Test
    @Transactional
    public void testAdd_fail_nameIsInvalidFormat() throws Exception{
        //given
        String url = "/members/add";
        String invalidName = "김yong환";
        //when
        ResultActions resultActions = this.mockMvc.perform(post(url)
                                                .param("name", invalidName)
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
                                                .andExpect(status().is2xxSuccessful());
        String actual = resultActions.andReturn().getModelAndView().getModel().get("valid_name").toString();
        //then
        assertThat(actual).isEqualTo("한글 또는 영문을 입력해주세요");
    }

    @Test
    @Transactional
    public void testAdd_fail_birthdayIsNull() throws Exception{
        //given
        String url = "/members/add";
        String birthday = null;
        //when
        ResultActions resultActions = this.mockMvc.perform(post(url)
                                                .param("name", "김용환")
                                                .param("birthday", birthday)
                                                .param("phone", "010-1234-5678")
                                                .param("zipcode", "06288")
                                                .param("street", "서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)")
                                                .param("detail", "")
                                                .param("email", "user102@gmail.com")
                                                .param("userId", "user101")
                                                .param("password", "password12345@")
                                                .param("password_confirm", "password12345@")
                                                .param("gender", "male")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .with(csrf()))
                                                .andExpect(status().is2xxSuccessful());
        String actual = resultActions.andReturn().getModelAndView().getModel().get("valid_birthday").toString();
        //then
        assertThat(actual).isEqualTo("필수 정보입니다.");
    }

    @Test
    @Transactional
    public void testAdd_fail_birthdayIsAfterToday() throws Exception{
        //given
        String url = "/members/add";
        String tomorrow = LocalDate.now().plusDays(1).toString();
        //when
        ResultActions resultActions = this.mockMvc.perform(post(url)
                                                .param("name", "김용환")
                                                .param("birthday", tomorrow)
                                                .param("phone", "010-1234-5678")
                                                .param("zipcode", "06288")
                                                .param("street", "서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)")
                                                .param("detail", "")
                                                .param("email", "user102@gmail.com")
                                                .param("userId", "user101")
                                                .param("password", "password12345@")
                                                .param("password_confirm", "password12345@")
                                                .param("gender", "male")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .with(csrf()))
                                                .andExpect(status().is2xxSuccessful());
        String actual = resultActions.andReturn().getModelAndView().getModel().get("valid_birthday").toString();
        //then
        assertThat(actual).isEqualTo("태어난 연도는 현재 일자 이전내에 입력 해주세요");
    }

    @Test
    @Transactional
    public void testAdd_fail_phoneIsEmpty() throws Exception{
        //given
        String url = "/members/add";
        String emptyPhone = "";
        //when
        ResultActions resultActions = this.mockMvc.perform(post(url)
                                                .param("name", "김용환")
                                                .param("birthday", "2022-09-09")
                                                .param("phone", emptyPhone)
                                                .param("zipcode", "06288")
                                                .param("street", "서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)")
                                                .param("detail", "")
                                                .param("email", "user102@gmail.com")
                                                .param("userId", "user101")
                                                .param("password", "password12345@")
                                                .param("password_confirm", "password12345@")
                                                .param("gender", "male")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .with(csrf()))
                                                .andExpect(status().is2xxSuccessful());
        String actual = resultActions.andReturn().getModelAndView().getModel().get("valid_phone").toString();
        //then
        assertThat(actual).isEqualTo("010-0000-0000 형식으로 입력해주세요");
    }

    @Test
    @Transactional
    public void testAdd_fail_phoneIsInvalidFormat() throws Exception{
        //given
        String url = "/members/add";
        String invalidPhone = "010-999-9999";
        //when
        ResultActions resultActions = this.mockMvc.perform(post(url)
                                                .param("name", "김용환")
                                                .param("birthday", "2022-09-09")
                                                .param("phone", invalidPhone)
                                                .param("zipcode", "06288")
                                                .param("street", "서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)")
                                                .param("detail", "")
                                                .param("email", "user102@gmail.com")
                                                .param("userId", "user101")
                                                .param("password", "password12345@")
                                                .param("password_confirm", "password12345@")
                                                .param("gender", "male")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .with(csrf()))
                                                .andExpect(status().is2xxSuccessful());
        String actual = resultActions.andReturn().getModelAndView().getModel().get("valid_phone").toString();
        //then
        assertThat(actual).isEqualTo("010-0000-0000 형식으로 입력해주세요");
    }

    @Test
    @Transactional
    public void testAdd_fail_phoneIsDuplicated() throws Exception{
        //given
        String url = "/members/add";
        String duplicatedPhone = "010-6261-7437";
        //when
        ResultActions resultActions = this.mockMvc.perform(post(url)
                                                .param("name", "김용환")
                                                .param("birthday", "2022-09-09")
                                                .param("phone", duplicatedPhone)
                                                .param("zipcode", "06288")
                                                .param("street", "서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)")
                                                .param("detail", "")
                                                .param("email", "user102@gmail.com")
                                                .param("userId", "user101")
                                                .param("password", "password12345@")
                                                .param("password_confirm", "password12345@")
                                                .param("gender", "male")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .with(csrf()))
                                                .andExpect(status().is2xxSuccessful());
        String actual = resultActions.andReturn().getModelAndView().getModel().get("valid_phone").toString();
        //then
        assertThat(actual).isEqualTo("이미 사용중인 연락처 입니다.");
    }

    @Test
    @Transactional
    public void testAdd_fail_zipcodeIsEmpty() throws Exception{
        //given
        String url = "/members/add";
        String emptyZipcode = "";
        //when
        ResultActions resultActions = this.mockMvc.perform(post(url)
                                                .param("name", "김용환")
                                                .param("birthday", "2022-09-09")
                                                .param("phone", "010-9999-9999")
                                                .param("zipcode", emptyZipcode)
                                                .param("street", "")
                                                .param("detail", "")
                                                .param("email", "user102@gmail.com")
                                                .param("userId", "user101")
                                                .param("password", "password12345@")
                                                .param("password_confirm", "password12345@")
                                                .param("gender", "male")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .with(csrf()))
                                                .andExpect(status().is2xxSuccessful());
        String actual = resultActions.andReturn().getModelAndView().getModel().get("valid_zipcode").toString();
        //then
        assertThat(actual).isEqualTo("필수 정보입니다.");
    }

    @Test
    @Transactional
    public void testAdd_fail_emailIsEmpty() throws Exception{
        //given
        String url = "/members/add";
        String emptyEmail = "";
        //when
        ResultActions resultActions = this.mockMvc.perform(post(url)
                                                .param("name", "김용환")
                                                .param("birthday", "2022-09-09")
                                                .param("phone", "010-9999-9999")
                                                .param("zipcode", "06288")
                                                .param("street", "서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)")
                                                .param("detail", "")
                                                .param("email", emptyEmail)
                                                .param("userId", "user101")
                                                .param("password", "password12345@")
                                                .param("password_confirm", "password12345@")
                                                .param("gender", "male")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .with(csrf()))
                                                .andExpect(status().is2xxSuccessful());
        String actual = resultActions.andReturn().getModelAndView().getModel().get("valid_email").toString();
        //then
        assertThat(actual).isEqualTo("필수 정보입니다.");
    }

    @Test
    @Transactional
    public void testAdd_fail_emailIsInvalidFormat() throws Exception{
        //given
        String url = "/members/add";
        String invalidEmail = "user101gmail.com";
        //when
        ResultActions resultActions = this.mockMvc.perform(post(url)
                                                .param("name", "김용환")
                                                .param("birthday", "2022-09-09")
                                                .param("phone", "010-9999-9999")
                                                .param("zipcode", "06288")
                                                .param("street", "서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)")
                                                .param("detail", "")
                                                .param("email", invalidEmail)
                                                .param("userId", "user101")
                                                .param("password", "password12345@")
                                                .param("password_confirm", "password12345@")
                                                .param("gender", "male")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .with(csrf()))
                                                .andExpect(status().is2xxSuccessful());
        String actual = resultActions.andReturn().getModelAndView().getModel().get("valid_email").toString();
        //then
        assertThat(actual).isEqualTo("이메일 주소를 다시 확인해주세요");
    }

    @Test
    @Transactional
    public void testAdd_fail_emailIsDuplicated() throws Exception{
        //given
        String url = "/members/add";
        String duplicatedEmail = "user1@gmail.com";
        //when
        ResultActions resultActions = this.mockMvc.perform(post(url)
                                                .param("name", "김용환")
                                                .param("birthday", "2022-09-09")
                                                .param("phone", "010-9999-9999")
                                                .param("zipcode", "06288")
                                                .param("street", "서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)")
                                                .param("detail", "")
                                                .param("email", duplicatedEmail)
                                                .param("userId", "user101")
                                                .param("password", "password12345@")
                                                .param("password_confirm", "password12345@")
                                                .param("gender", "male")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .with(csrf()))
                                                .andExpect(status().is2xxSuccessful());
        String actual = resultActions.andReturn().getModelAndView().getModel().get("valid_email").toString();
        //then
        assertThat(actual).isEqualTo("이미 사용중인 이메일 입니다.");
    }

    @Test
    @Transactional
    public void testAdd_fail_userIdIsEmpty() throws Exception{
        //given
        String url = "/members/add";
        String emptyUserId = "";
        //when
        ResultActions resultActions = this.mockMvc.perform(post(url)
                                                .param("name", "김용환")
                                                .param("birthday", "2022-09-09")
                                                .param("phone", "010-1234-5678")
                                                .param("zipcode", "06288")
                                                .param("street", "서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)")
                                                .param("detail", "")
                                                .param("email", "user102@gmail.com")
                                                .param("userId", emptyUserId)
                                                .param("password", "password12345@")
                                                .param("password_confirm", "password12345@")
                                                .param("gender", "male")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .with(csrf()))
                                                .andExpect(status().is2xxSuccessful());
        String actual = resultActions.andReturn().getModelAndView().getModel().get("valid_userId").toString();
        //then
        assertThat(actual).isEqualTo("영문자, 숫자 4~20자 이내 입력해주세요");
    }

    @Test
    @Transactional
    public void testAdd_fail_userIdIsInvalidFormat() throws Exception{
        //given
        String url = "/members/add";
        String invalidFormatUserId = "user1aiowejfoajweofjiawoefjowje";
        //when
        ResultActions resultActions = this.mockMvc.perform(post(url)
                                                .param("name", "김용환")
                                                .param("birthday", "2022-09-09")
                                                .param("phone", "010-1234-5678")
                                                .param("zipcode", "06288")
                                                .param("street", "서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)")
                                                .param("detail", "")
                                                .param("email", "user102@gmail.com")
                                                .param("userId", invalidFormatUserId)
                                                .param("password", "password12345@")
                                                .param("password_confirm", "password12345@")
                                                .param("gender", "male")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .with(csrf()))
                                                .andExpect(status().is2xxSuccessful());
        String actual = resultActions.andReturn().getModelAndView().getModel().get("valid_userId").toString();
        //then
        assertThat(actual).isEqualTo("영문자, 숫자 4~20자 이내 입력해주세요");
    }

    @Test
    @Transactional
    public void testAdd_fail_userIdIsDuplicate() throws Exception{
        //given
        String url = "/members/add";
        String duplicatedUserId = "user1";
        //when
        ResultActions resultActions = this.mockMvc.perform(post(url)
                                                .param("name", "김용환")
                                                .param("birthday", "2022-09-09")
                                                .param("phone", "010-1234-5678")
                                                .param("zipcode", "06288")
                                                .param("street", "서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)")
                                                .param("detail", "")
                                                .param("email", "user102@gmail.com")
                                                .param("userId", duplicatedUserId)
                                                .param("password", "password12345@")
                                                .param("password_confirm", "password12345@")
                                                .param("gender", "male")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .with(csrf()))
                                                .andExpect(status().is2xxSuccessful());
        String actual = resultActions.andReturn().getModelAndView().getModel().get("valid_userId").toString();
        //then
        assertThat(actual).isEqualTo("이미 사용중인 아이디 입니다.");
    }

    @Test
    @Transactional
    public void testAdd_fail_passwordIsEmpty() throws Exception{
        //given
        String url = "/members/add";
        String emptyPassword = "";
        //when
        ResultActions resultActions = this.mockMvc.perform(post(url)
                                                .param("name", "김용환")
                                                .param("birthday", "2022-09-09")
                                                .param("phone", "010-1234-5678")
                                                .param("zipcode", "06288")
                                                .param("street", "서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)")
                                                .param("detail", "")
                                                .param("email", "user102@gmail.com")
                                                .param("userId", "user101")
                                                .param("password", emptyPassword)
                                                .param("password_confirm", emptyPassword)
                                                .param("gender", "male")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .with(csrf()))
                                                .andExpect(status().is2xxSuccessful());
        String actual = resultActions.andReturn().getModelAndView().getModel().get("valid_password").toString();
        //then
        assertThat(actual).isEqualTo("비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.");
    }

    @Test
    @Transactional
    public void testAdd_fail_passwordIsInvalidFormat() throws Exception{
        //given
        String url = "/members/add";
        String invalidFormat = "awefoiajwefoijaweofjaowiefjoweijfowiejfowjief";
        //when
        ResultActions resultActions = this.mockMvc.perform(post(url)
                                                .param("name", "김용환")
                                                .param("birthday", "2022-09-09")
                                                .param("phone", "010-1234-5678")
                                                .param("zipcode", "06288")
                                                .param("street", "서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)")
                                                .param("detail", "")
                                                .param("email", "user102@gmail.com")
                                                .param("userId", "user101")
                                                .param("password", invalidFormat)
                                                .param("password_confirm", invalidFormat)
                                                .param("gender", "male")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .with(csrf()))
                                                .andExpect(status().is2xxSuccessful());
        String actual = resultActions.andReturn().getModelAndView().getModel().get("valid_password").toString();
        //then
        assertThat(actual).isEqualTo("비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.");
    }

    @Test
    @Transactional
    public void testAdd_fail_passwordAndPasswordConfirmIsNotEqual() throws Exception{
        //given
        String url = "/members/add";
        String password = "password12345@";
        String password_confirm = "password12";
        //when
        ResultActions resultActions = this.mockMvc.perform(post(url)
                                                .param("name", "김용환")
                                                .param("birthday", "2022-09-09")
                                                .param("phone", "010-1234-5678")
                                                .param("zipcode", "06288")
                                                .param("street", "서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)")
                                                .param("detail", "")
                                                .param("email", "user102@gmail.com")
                                                .param("userId", "user101")
                                                .param("password", password)
                                                .param("password_confirm", password_confirm)
                                                .param("gender", "male")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .with(csrf()))
                                                .andExpect(status().is2xxSuccessful());
        String actual = resultActions.andReturn().getModelAndView().getModel().get("valid_password_confirm").toString();
        //then
        assertThat(actual).isEqualTo("비밀번호가 일치하지 않습니다.");
    }

    @Test
    public void testView() throws Exception {
        //given
        String url = "/members/view";
        String memberId = "1";
        //when
        MemberDTO member = (MemberDTO) this.mockMvc.perform(get(url)
                                             .param("memberId", memberId)
                                             .contentType(MediaType.TEXT_HTML))
                                             .andExpect(status().isOk())
                                             .andReturn().getModelAndView().getModel().get("member");
        //then
        assertThat(member.getId()).isEqualTo(Long.parseLong(memberId));
    }
    
    @Test
    public void testModifyForm() throws Exception {
        //given
        String url = "/members/modify";
        String memberId = "1";
        //when
        MemberDTO form = (MemberDTO) this.mockMvc.perform(get(url)
                                                   .param("memberId", memberId)
                                                   .contentType(MediaType.TEXT_HTML))
                                                   .andExpect(status().isOk())
                                                   .andReturn().getModelAndView().getModel().get("form");
        //then
        assertThat(form.getId()).isEqualTo(Long.parseLong(memberId));

    }

    @Test
    @Transactional
    public void testModify() throws Exception {
        //given
        String url = "/members/modify";
        String memberId = "1";
        String modifiedName = "홍길동";
        //when
        String msg = (String) this.mockMvc.perform(post(url)
                                             .param("id",memberId)
                                             .param("name", modifiedName)
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
                                             .get("msg");
        //then
        assertThat(msg).isEqualTo("success");
    }
    
    @Test
    @Transactional
    public void testDelete() throws Exception {
        //given
        String url = "/members/delete";
        String memberId = "1";
        //when
        String msg = (String) this.mockMvc.perform(post(url)
                                          .param("id", memberId)
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .with(csrf()))
                                          .andExpect(status().is3xxRedirection())
                                          .andReturn()
                                          .getFlashMap()
                                          .get("msg");
        //then
        assertThat(msg).isEqualTo("success");
    }

    @Test
    @Transactional
    public void testDeletes() throws Exception {
        //given
        String url = "/members/deletes";
        String[] memberIds = {"1","2"};
        //when
        String msg = (String) this.mockMvc.perform(post(url)
                                          .param("checks", memberIds)
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .with(csrf()))
                                          .andExpect(status().is3xxRedirection())
                                          .andReturn().getFlashMap().get("msg");
        //then
        assertThat(msg).isEqualTo("success");
    }
}