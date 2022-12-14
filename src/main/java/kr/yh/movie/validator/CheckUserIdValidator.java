package kr.yh.movie.validator;

import kr.yh.movie.controller.member.MemberDTO;
import kr.yh.movie.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
@Log
public class CheckUserIdValidator extends AbstractValidator<MemberDTO>{
    private final MemberRepository memberRepository;
    @Override
    protected void doValidate(MemberDTO dto, Errors errors) {
        log.info("MemberForm dto : " + dto);
        if(memberRepository.existByUserId(dto.getUserId())){
            errors.rejectValue("userId", "아이디 중복 오류", "이미 사용중인 아이디 입니다.");
        }
    }
}
