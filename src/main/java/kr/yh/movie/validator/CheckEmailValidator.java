package kr.yh.movie.validator;

import kr.yh.movie.controller.member.MemberForm;
import kr.yh.movie.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckEmailValidator extends AbstractValidator<MemberForm>{
    private final MemberRepository memberRepository;
    @Override
    protected void doValidate(MemberForm dto, Errors errors) {
        if(memberRepository.existByEmail(dto.getEmail())){
            errors.rejectValue("email", "이메일 중복 오류", "이미 사용중인 이메일 입니다.");
        }
    }
}
