package kr.yh.movie.controller.member;

import kr.yh.movie.domain.member.Member;
import kr.yh.movie.domain.member.MemberRole;
import kr.yh.movie.util.MemberRegExpUtils;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static kr.yh.movie.util.MemberRegExpUtils.*;
import static kr.yh.movie.util.ModelMapperUtils.getModelMapper;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberDTO {
    private Long id;
    @Pattern(regexp = NAME_FORMAT,
                message = "한글 또는 영문을 입력해주세요")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    @Pattern(regexp = PHONE_FORMAT,
            message = "010-0000-0000 형식으로 입력해주세요")
    private String phone;
    @NotEmpty(message = "필수 정보입니다.")
    private String zipcode;
    @NotEmpty(message = "필수 정보입니다.")
    private String street;
    private String detail;
    @NotEmpty(message = "필수 정보입니다.")
    @Email(message = "이메일 주소를 다시 확인해주세요")
    private String email;
    @Pattern(regexp = USERID_FORMAT, message = "영문자, 숫자 4~20자 이내 입력해주세요")
    private String userId;
    @Pattern(regexp = PASSWORD_FORMAT,
            message = "비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;
    @NotEmpty(message = "필수 정보입니다.")
    private String password_confirm;
    private String gender;
    private MemberRole roleName;

    public MemberDTO(Member member){
        this.id = member.getId();
        this.name = member.getName();
        this.birthday = member.getBirthday();
        this.phone = member.getPhone();
        this.zipcode = member.getAddress().getZipcode();
        this.street = member.getAddress().getStreet();
        this.detail = member.getAddress().getDetail();
        this.email = member.getEmail();
        this.userId = member.getUserId();
        this.password = member.getPassword();
        this.gender = member.getGender();
        this.roleName = member.getRoleName();
    }

    public static MemberDTO createMemberDTO(){
        return new MemberDTO();
    }

    public static MemberDTO of(Member member){
        ModelMapper modelMapper = getModelMapper();
        modelMapper.typeMap(Member.class, MemberDTO.class).addMappings(mapper->{
           mapper.map(src->src.getAddress().getZipcode(), MemberDTO::setZipcode);
            mapper.map(src->src.getAddress().getStreet(), MemberDTO::setStreet);
            mapper.map(src->src.getAddress().getDetail(), MemberDTO::setDetail);
        });
        return modelMapper.map(member, MemberDTO.class);
    }
}
