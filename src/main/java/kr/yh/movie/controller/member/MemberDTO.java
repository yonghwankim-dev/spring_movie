package kr.yh.movie.controller.member;

import kr.yh.movie.domain.Reservation;
import kr.yh.movie.domain.member.Address;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.domain.member.MemberRole;
import kr.yh.movie.util.ModelMapperUtils;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static kr.yh.movie.util.ModelMapperUtils.*;
import static org.modelmapper.config.Configuration.AccessLevel.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberDTO {
    private Long id;
    @NotEmpty(message = "회원 이름을 입력해주세요")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    @NotEmpty(message = "핸드폰 번호를 입력해주세요")
    private String phone;
    @NotEmpty(message = "주소를 입력해주세요")
    private String zipcode;
    @NotEmpty(message = "주소를 입력해주세요")
    private String street;
    private String detail;
    @NotEmpty(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식이 올바르지 않습니다")
    private String email;
    @NotEmpty(message = "아이디를 입력해주세요")
    private String userId;
    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;
    @NotEmpty(message = "비밀번호를 입력해주세요")
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
