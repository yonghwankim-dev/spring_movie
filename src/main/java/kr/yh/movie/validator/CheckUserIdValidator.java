package kr.yh.movie.validator;

import kr.yh.movie.controller.MemberForm;
import kr.yh.movie.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckUserIdValidator extends AbstractValidator<MemberForm>{
    private final MemberRepository memberRepository;
    @Override
    protected void doValidate(MemberForm dto, Errors errors) {
        if(memberRepository.existByUserId(dto.getUserId())){
            errors.rejectValue("userId", "아이디 중복 오류", "이미 사용중인 아이디 입니다.");
        }
    }
}
