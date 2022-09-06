package kr.yh.movie.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
public class MemberForm {
    @NotEmpty(message = "회원 이름을 입력해주세요")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
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
    @NotEmpty(message = "성별을 선택해주세요")
    private String gender;

}
