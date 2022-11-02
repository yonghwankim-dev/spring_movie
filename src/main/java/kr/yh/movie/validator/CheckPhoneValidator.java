package kr.yh.movie.validator;

import kr.yh.movie.controller.member.MemberDTO;
import kr.yh.movie.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckPhoneValidator extends AbstractValidator<MemberDTO>{
    private final MemberRepository memberRepository;
    @Override
    protected void doValidate(MemberDTO dto, Errors errors) {
        if(memberRepository.existByPhone(dto.getPhone())){
            errors.rejectValue("phone", "연락처 중복 오류", "이미 사용중인 연락처 입니다.");
        }
    }
}
