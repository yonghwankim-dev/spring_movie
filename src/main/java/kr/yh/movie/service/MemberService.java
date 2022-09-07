package kr.yh.movie.service;

import kr.yh.movie.controller.MemberForm;
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
        memberRepository.save(member);
    }

    // 회원가입시 유효성 체크
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    // 아이디 중복 여부 확인
    public void checkUserIdDuplication(MemberForm memberForm){
        boolean userIdDuplicate = memberRepository.existByUserId(memberForm.getUserId());
        if(userIdDuplicate){
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    // 핸드폰번호 중복 여부 확인
    public void checkPhoneDuplication(MemberForm memberForm){
        boolean phoneDuplicate = memberRepository.existByPhone(memberForm.getPhone());
        if(phoneDuplicate){
            throw new IllegalStateException("이미 존재하는 핸드폰번호입니다.");
        }
    }

    // 이메일 중복 여부 확인
    public void checkEmailDuplication(MemberForm memberForm){
        boolean emailDuplicate = memberRepository.existByEmail(memberForm.getEmail());
        if(emailDuplicate){
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }



    // 로그인
    public void login(Member member){

    }

    // 로그아웃
    public void logout(){

    }




}
