package kr.yh.movie.controller;

import com.querydsl.core.BooleanBuilder;
import kr.yh.movie.controller.member.MemberController;
import kr.yh.movie.controller.member.MemberDTO;
import kr.yh.movie.domain.member.Address;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.domain.member.QMember;
import kr.yh.movie.repository.MemberRepository;
import kr.yh.movie.service.MemberService;
import kr.yh.movie.vo.PageVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static kr.yh.movie.util.RegExpMsgUtils.ASSERTIVE_INFO_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MemberController.class,
        includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "kr.yh.movie.validator.*"),
        excludeFilters = @ComponentScan.Filter(type= FilterType.REGEX, pattern = "kr.yh.movie.controller.converter.*"))
@Import(BCryptPasswordEncoder.class)
@WithMockUser
public class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @MockBean
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    Member fakeMember;

    @BeforeEach
    public void setup(){
        fakeMember = Member.builder()
                .id(1L)
                .birthday(LocalDate.of(1990,1,1))
                .phone("010-1234-5678")
                .address(new Address("06288", "서울특별시 강남구 삼성로 154 (대치동, 강남구의회, 강남구민회관)", ""))
                .email("fakeuser@gmail.com")
                .userId("fakeuser")
                .password(passwordEncoder.encode("fakeuser1234@"))
                .gender("male")
                .name("김용환")
                .build();
    }

    @Test
    public void testList() throws Exception {
        //given
        String url = "/members/list";
        PageVO pageVO = new PageVO();
        Pageable page = pageVO.makePageable(0, "id");
        BooleanBuilder builder = new BooleanBuilder();
        QMember member = QMember.member;
        builder.and(member.id.gt(0));
        Page<Member> fakePage = new PageImpl<>(new ArrayList<>(), page, 10);
        //mocking
        when(memberService.makePredicates(pageVO.getType(), pageVO.getKeyword())).thenReturn(builder);
        when(memberService.findAll(builder, page)).thenReturn(fakePage);

        //when
        this.mockMvc.perform(get(url))
                     .andExpect(status().isOk())
                     .andExpect(model().attributeExists("result"));
        //then
    }

    @Test
    public void testAddForm() throws Exception{
        //given
        String url = "/members/add";
        //when
        this.mockMvc.perform(get(url)
                   .contentType(MediaType.TEXT_HTML))
                   .andExpect(status().isOk())
                   .andExpect(model().attributeExists("form"));
        //then
    }

    @Test
    public void testAdd_success() throws Exception{
        //given
        String url = "/members/add";
        MemberDTO memberDTO = MemberDTO.of(fakeMember);
        //mocking
        when(memberService.save(fakeMember)).thenReturn(fakeMember);
        //when
        MemberDTO savedMember = (MemberDTO) this.mockMvc.perform(post(url)
                                                  .param("id", String.valueOf(memberDTO.getId()))
                                                  .param("name", memberDTO.getName())
                                                  .param("birthday", memberDTO.getBirthday().toString())
                                                  .param("phone", memberDTO.getPhone())
                                                  .param("zipcode", memberDTO.getZipcode())
                                                  .param("street", memberDTO.getStreet())
                                                  .param("detail", memberDTO.getDetail())
                                                  .param("email", memberDTO.getEmail())
                                                  .param("userId", memberDTO.getUserId())
                                                  .param("password", "fakeuser1234@")
                                                  .param("password_confirm", "fakeuser1234@")
                                                  .param("gender", memberDTO.getGender())
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .with(csrf()))
                                                  .andExpect(status().is3xxRedirection())
                                                  .andReturn()
                                                  .getFlashMap()
                                                  .get("savedMember");
        //then
        assertThat(Member.of(savedMember)).isEqualTo(fakeMember);
    }

    @Test
    public void testAdd_fail_nameIsEmpty() throws Exception{
        //given
        String url = "/members/add";
        String emptyName = "";
        //when
        this.mockMvc.perform(post(url)
                    .param("name", emptyName)
                    .param("birthday", fakeMember.getBirthday().toString())
                    .param("phone", fakeMember.getPhone())
                    .param("zipcode", fakeMember.getAddress().getZipcode())
                    .param("street", fakeMember.getAddress().getStreet())
                    .param("detail", fakeMember.getAddress().getDetail())
                    .param("email", fakeMember.getEmail())
                    .param("userId", fakeMember.getUserId())
                    .param("password", fakeMember.getPassword())
                    .param("password_confirm", fakeMember.getPassword())
                    .param("gender", fakeMember.getGender())
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attribute("valid_name", "한글 또는 영문을 입력해주세요"));
    }

    @Test
    public void testAdd_fail_nameIsInvalidFormat() throws Exception{
        //given
        String url = "/members/add";
        String invalidName = "김yong환";
        //when
        this.mockMvc.perform(post(url)
                    .param("name", invalidName)
                    .param("birthday", fakeMember.getBirthday().toString())
                    .param("phone", fakeMember.getPhone())
                    .param("zipcode", fakeMember.getAddress().getZipcode())
                    .param("street", fakeMember.getAddress().getStreet())
                    .param("detail", fakeMember.getAddress().getDetail())
                    .param("email", fakeMember.getEmail())
                    .param("userId", fakeMember.getUserId())
                    .param("password", fakeMember.getPassword())
                    .param("password_confirm", fakeMember.getPassword())
                    .param("gender", fakeMember.getGender())
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attribute("valid_name", "한글 또는 영문을 입력해주세요"));
    }

    @Test
    public void testAdd_fail_birthdayIsNull() throws Exception{
        //given
        String url = "/members/add";
        String birthday = null;
        //when
        this.mockMvc.perform(post(url)
                    .param("name", fakeMember.getName())
                    .param("birthday", birthday)
                    .param("phone", fakeMember.getPhone())
                    .param("zipcode", fakeMember.getAddress().getZipcode())
                    .param("street", fakeMember.getAddress().getStreet())
                    .param("detail", fakeMember.getAddress().getDetail())
                    .param("email", fakeMember.getEmail())
                    .param("userId", fakeMember.getUserId())
                    .param("password", fakeMember.getPassword())
                    .param("password_confirm", fakeMember.getPassword())
                    .param("gender", fakeMember.getGender())
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attribute("valid_birthday", "필수 정보입니다."));
    }

    @Test
    public void testAdd_fail_birthdayIsAfterToday() throws Exception{
        //given
        String url = "/members/add";
        String tomorrow = LocalDate.now().plusDays(1).toString();
        //when
        this.mockMvc.perform(post(url)
                    .param("name", fakeMember.getName())
                    .param("birthday", tomorrow)
                    .param("phone", fakeMember.getPhone())
                    .param("zipcode", fakeMember.getAddress().getZipcode())
                    .param("street", fakeMember.getAddress().getStreet())
                    .param("detail", fakeMember.getAddress().getDetail())
                    .param("email", fakeMember.getEmail())
                    .param("userId", fakeMember.getUserId())
                    .param("password", fakeMember.getPassword())
                    .param("password_confirm", fakeMember.getPassword())
                    .param("gender", fakeMember.getGender())
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attribute("valid_birthday", "태어난 연도는 현재 일자 이전내에 입력 해주세요"));
    }

    @Test
    public void testAdd_fail_phoneIsEmpty() throws Exception{
        //given
        String url = "/members/add";
        String emptyPhone = "";
        //when
        this.mockMvc.perform(post(url)
                    .param("name", fakeMember.getName())
                    .param("birthday", fakeMember.getBirthday().toString())
                    .param("phone", emptyPhone)
                    .param("zipcode", fakeMember.getAddress().getZipcode())
                    .param("street", fakeMember.getAddress().getStreet())
                    .param("detail", fakeMember.getAddress().getDetail())
                    .param("email", fakeMember.getEmail())
                    .param("userId", fakeMember.getUserId())
                    .param("password", fakeMember.getPassword())
                    .param("password_confirm", fakeMember.getPassword())
                    .param("gender", fakeMember.getGender())
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attribute("valid_phone", "010-0000-0000 형식으로 입력해주세요"));
    }

    @Test
    public void testAdd_fail_phoneIsInvalidFormat() throws Exception{
        //given
        String url = "/members/add";
        String invalidPhone = "010-999-9999";
        //when
        this.mockMvc.perform(post(url)
                    .param("name", fakeMember.getName())
                    .param("birthday", fakeMember.getBirthday().toString())
                    .param("phone", invalidPhone)
                    .param("zipcode", fakeMember.getAddress().getZipcode())
                    .param("street", fakeMember.getAddress().getStreet())
                    .param("detail", fakeMember.getAddress().getDetail())
                    .param("email", fakeMember.getEmail())
                    .param("userId", fakeMember.getUserId())
                    .param("password", fakeMember.getPassword())
                    .param("password_confirm", fakeMember.getPassword())
                    .param("gender", fakeMember.getGender())
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attribute("valid_phone", "010-0000-0000 형식으로 입력해주세요"));
    }

    @Test
    public void testAdd_fail_zipcodeIsEmpty() throws Exception{
        //given
        String url = "/members/add";
        String emptyZipcode = "";
        //when
        this.mockMvc.perform(post(url)
                        .param("name", fakeMember.getName())
                        .param("birthday", fakeMember.getBirthday().toString())
                        .param("phone", fakeMember.getPhone())
                        .param("zipcode", emptyZipcode)
                        .param("street", fakeMember.getAddress().getStreet())
                        .param("detail", fakeMember.getAddress().getDetail())
                        .param("email", fakeMember.getEmail())
                        .param("userId", fakeMember.getUserId())
                        .param("password", fakeMember.getPassword())
                        .param("password_confirm", fakeMember.getPassword())
                        .param("gender", fakeMember.getGender())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                        .andExpect(status().is2xxSuccessful())
                        .andExpect(model().attribute("valid_zipcode", "필수 정보입니다."));
    }

    @Test
    public void testAdd_fail_emailIsEmpty() throws Exception{
        //given
        String url = "/members/add";
        String emptyEmail = "";
        //when
        this.mockMvc.perform(post(url)
                    .param("name", fakeMember.getName())
                    .param("birthday", fakeMember.getBirthday().toString())
                    .param("phone", fakeMember.getPhone())
                    .param("zipcode", fakeMember.getAddress().getZipcode())
                    .param("street", fakeMember.getAddress().getStreet())
                    .param("detail", fakeMember.getAddress().getDetail())
                    .param("email", emptyEmail)
                    .param("userId", fakeMember.getUserId())
                    .param("password", fakeMember.getPassword())
                    .param("password_confirm", fakeMember.getPassword())
                    .param("gender", fakeMember.getGender())
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attribute("valid_email", ASSERTIVE_INFO_MSG));
    }

    @Test
    public void testAdd_fail_emailIsInvalidFormat() throws Exception{
        //given
        String url = "/members/add";
        String invalidEmail = "user101gmail.com";
        //when
        this.mockMvc.perform(post(url)
                    .param("name", fakeMember.getName())
                    .param("birthday", fakeMember.getBirthday().toString())
                    .param("phone", fakeMember.getPhone())
                    .param("zipcode", fakeMember.getAddress().getZipcode())
                    .param("street", fakeMember.getAddress().getStreet())
                    .param("detail", fakeMember.getAddress().getDetail())
                    .param("email", invalidEmail)
                    .param("userId", fakeMember.getUserId())
                    .param("password", fakeMember.getPassword())
                    .param("password_confirm", fakeMember.getPassword())
                    .param("gender", fakeMember.getGender())
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attribute("valid_email", "이메일 주소를 다시 확인해주세요"));
    }

    @Test
    public void testAdd_fail_userIdIsEmpty() throws Exception{
        //given
        String url = "/members/add";
        String emptyUserId = "";
        //when
        this.mockMvc.perform(post(url)
                    .param("name", fakeMember.getName())
                    .param("birthday", fakeMember.getBirthday().toString())
                    .param("phone", fakeMember.getPhone())
                    .param("zipcode", fakeMember.getAddress().getZipcode())
                    .param("street", fakeMember.getAddress().getStreet())
                    .param("detail", fakeMember.getAddress().getDetail())
                    .param("email", fakeMember.getEmail())
                    .param("userId", emptyUserId)
                    .param("password", fakeMember.getPassword())
                    .param("password_confirm", fakeMember.getPassword())
                    .param("gender", fakeMember.getGender())
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attribute("valid_userId", "영문자, 숫자 4~20자 이내 입력해주세요"));
    }

    @Test
    public void testAdd_fail_userIdIsInvalidFormat() throws Exception{
        //given
        String url = "/members/add";
        String invalidFormatUserId = "user1aiowejfoajweofjiawoefjowje";
        //when
        this.mockMvc.perform(post(url)
                    .param("name", fakeMember.getName())
                    .param("birthday", fakeMember.getBirthday().toString())
                    .param("phone", fakeMember.getPhone())
                    .param("zipcode", fakeMember.getAddress().getZipcode())
                    .param("street", fakeMember.getAddress().getStreet())
                    .param("detail", fakeMember.getAddress().getDetail())
                    .param("email", fakeMember.getEmail())
                    .param("userId", invalidFormatUserId)
                    .param("password", fakeMember.getPassword())
                    .param("password_confirm", fakeMember.getPassword())
                    .param("gender", fakeMember.getGender())
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attribute("valid_userId", "영문자, 숫자 4~20자 이내 입력해주세요"));
    }

    @Test
    public void testAdd_fail_passwordIsEmpty() throws Exception{
        //given
        String url = "/members/add";
        String emptyPassword = "";
        //when
        this.mockMvc.perform(post(url)
                    .param("name", fakeMember.getName())
                    .param("birthday", fakeMember.getBirthday().toString())
                    .param("phone", fakeMember.getPhone())
                    .param("zipcode", fakeMember.getAddress().getZipcode())
                    .param("street", fakeMember.getAddress().getStreet())
                    .param("detail", fakeMember.getAddress().getDetail())
                    .param("email", fakeMember.getEmail())
                    .param("userId", fakeMember.getUserId())
                    .param("password", emptyPassword)
                    .param("password_confirm", emptyPassword)
                    .param("gender", fakeMember.getGender())
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attribute("valid_password", "비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다."));
    }

    @Test
    public void testAdd_fail_passwordIsInvalidFormat() throws Exception{
        //given
        String url = "/members/add";
        String invalidFormat = "awefoiajwefoijaweofjaowiefjoweijfowiejfowjief";
        //when
        this.mockMvc.perform(post(url)
                    .param("name", fakeMember.getName())
                    .param("birthday", fakeMember.getBirthday().toString())
                    .param("phone", fakeMember.getPhone())
                    .param("zipcode", fakeMember.getAddress().getZipcode())
                    .param("street", fakeMember.getAddress().getStreet())
                    .param("detail", fakeMember.getAddress().getDetail())
                    .param("email", fakeMember.getEmail())
                    .param("userId", fakeMember.getUserId())
                    .param("password", invalidFormat)
                    .param("password_confirm", invalidFormat)
                    .param("gender", fakeMember.getGender())
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attribute("valid_password","비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다."));
    }

    @Test
    public void testAdd_fail_passwordAndPasswordConfirmIsNotEqual() throws Exception{
        //given
        String url = "/members/add";
        String password = "password12345@";
        String password_confirm = "password12";
        //when
        this.mockMvc.perform(post(url)
                    .param("name", fakeMember.getName())
                    .param("birthday", fakeMember.getBirthday().toString())
                    .param("phone", fakeMember.getPhone())
                    .param("zipcode", fakeMember.getAddress().getZipcode())
                    .param("street", fakeMember.getAddress().getStreet())
                    .param("detail", fakeMember.getAddress().getDetail())
                    .param("email", fakeMember.getEmail())
                    .param("userId", fakeMember.getUserId())
                    .param("password", password)
                    .param("password_confirm", password_confirm)
                    .param("gender", fakeMember.getGender())
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(model().attribute("valid_password_confirm", "비밀번호가 일치하지 않습니다."));
    }

    @Test
    public void testView() throws Exception {
        //given
        String url = "/members/view";
        String memberId = "1";
        //mocking
        when(memberService.findById(Long.valueOf(memberId))).thenReturn(Optional.ofNullable(fakeMember));
        //when
        this.mockMvc.perform(get(url)
                    .param("memberId", memberId)
                    .contentType(MediaType.TEXT_HTML))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("member"));
    }
    
    @Test
    public void testModifyForm() throws Exception {
        //given
        String url = "/members/modify";
        String memberId = "1";
        //mocking
        when(memberService.findById(Long.valueOf(memberId))).thenReturn(Optional.ofNullable(fakeMember));
        //when
        this.mockMvc.perform(get(url)
                    .param("memberId", memberId)
                    .contentType(MediaType.TEXT_HTML))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("form"));
    }

    @Test
    public void testModify() throws Exception {
        //given
        String url = "/members/modify";
        String memberId = "1";
        String modifiedName = "홍길동";
        fakeMember.setName(modifiedName);
        //mocking
        when(memberService.findById(Long.valueOf(memberId))).thenReturn(Optional.ofNullable(fakeMember));
        //when
        this.mockMvc.perform(post(url)
                    .param("id", memberId)
                    .param("name", modifiedName)
                    .param("birthday", fakeMember.getBirthday().toString())
                    .param("phone", fakeMember.getPhone())
                    .param("zipcode", fakeMember.getAddress().getZipcode())
                    .param("street", fakeMember.getAddress().getStreet())
                    .param("detail", fakeMember.getAddress().getDetail())
                    .param("email", fakeMember.getEmail())
                    .param("userId", fakeMember.getUserId())
                    .param("gender", fakeMember.getGender())
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(flash().attribute("msg", "success"));
        //then
    }
    
    @Test
    public void testDelete() throws Exception {
        //given
        String url = "/members/delete";
        String memberId = "1";
        //when
        this.mockMvc.perform(post(url)
                      .param("id", memberId)
                      .contentType(MediaType.APPLICATION_JSON)
                      .with(csrf()))
                      .andExpect(status().is3xxRedirection())
                      .andExpect(flash().attribute("msg", "success"));
    }

    @Test
    public void testDeletes() throws Exception {
        //given
        String url = "/members/deletes";
        String[] memberIds = {"1","2"};
        //when
        this.mockMvc.perform(post(url)
                    .param("checks", memberIds)
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(flash().attribute("msg", "success"));
    }
}