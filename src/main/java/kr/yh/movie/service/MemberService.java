package kr.yh.movie.service;

import kr.yh.movie.domain.Member;
import kr.yh.movie.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional
    public void signUp(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
    }

    // 중복회원검증
    private void validateDuplicateMember(Member member) {
        Member finedMember = memberRepository.findOneByUserId(member.getUserId());
        if(finedMember != null){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원가입시 유효성 체크
    public Map<String, String> validateHandling(BindingResult bindingResult) {
        Map<String, String> validatorResult = new HashMap<>();
        for(FieldError error : bindingResult.getFieldErrors()){
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }


    // 로그인
    public void login(Member member){

    }

    // 로그아웃
    public void logout(){

    }


}
