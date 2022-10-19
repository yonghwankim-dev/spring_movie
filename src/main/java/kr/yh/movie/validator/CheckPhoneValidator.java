package kr.yh.movie.validator;

import kr.yh.movie.controller.member.MemberForm;
import kr.yh.movie.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckPhoneValidator extends AbstractValidator<MemberForm>{
    private final MemberRepository memberRepository;
    @Override
    protected void doValidate(MemberForm dto, Errors errors) {
        if(memberRepository.existByPhone(dto.getPhone())){
            errors.rejectValue("phone", "연락처 중복 오류", "이미 사용중인 연락처 입니다.");
        }
    }
}
