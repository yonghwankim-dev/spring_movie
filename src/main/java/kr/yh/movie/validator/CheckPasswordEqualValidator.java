package kr.yh.movie.validator;

import kr.yh.movie.controller.member.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckPasswordEqualValidator extends AbstractValidator<MemberDTO>{
    @Override
    protected void doValidate(MemberDTO dto, Errors errors) {
        if(!dto.getPassword().equals(dto.getPassword_confirm())){
            errors.rejectValue("password_confirm", "비밀번호 일치 오류", "비밀번호가 일치하지 않습니다.");
        }
    }
}
