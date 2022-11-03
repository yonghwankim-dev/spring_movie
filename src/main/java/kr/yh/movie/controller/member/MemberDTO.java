package kr.yh.movie.controller.member;

import kr.yh.movie.domain.member.Member;
import kr.yh.movie.domain.member.MemberRole;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

import static kr.yh.movie.util.MemberRegExpMsgUtils.*;
import static kr.yh.movie.util.MemberRegExpUtils.*;
import static kr.yh.movie.util.ModelMapperUtils.getModelMapper;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberDTO {
    private Long id;
    @Pattern(regexp = NAME_FORMAT,
                message = NAME_FORMAT_MSG)
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    @Pattern(regexp = PHONE_FORMAT,
            message = PHONE_FORMAT_MSG)
    private String phone;
    @NotEmpty(message = ASSERTIVE_INFO_MSG)
    private String zipcode;
    @NotEmpty(message = ASSERTIVE_INFO_MSG)
    private String street;
    private String detail;
    @Email(message = EMAIL_FORMAT_MSG)
    private String email;
    @Pattern(regexp = USERID_FORMAT, message = USERID_FORMAT_MSG)
    private String userId;
    @Pattern(regexp = PASSWORD_FORMAT,
            message = PASSWORD_FORMAT_MSG)
    private String password;
    @NotEmpty(message = ASSERTIVE_INFO_MSG)
    private String password_confirm;
    private String gender;
    private MemberRole roleName;

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
