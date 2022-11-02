package kr.yh.movie.validator;

import kr.yh.movie.controller.member.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@Log
public class CheckBirthdayValidator extends AbstractValidator<MemberDTO>{
    @Override
    protected void doValidate(MemberDTO dto, Errors errors) {
        LocalDate today = LocalDateTime.now().toLocalDate();
        if(dto.getBirthday() == null){
            errors.rejectValue("birthday", "NotEmpty", "필수 정보입니다.");
        }else if(dto.getBirthday().isAfter(today)){
            errors.rejectValue("birthday", "생년월일 오류", "태어난 연도는 현재 일자 이전내에 입력 해주세요");
        }
    }
}
