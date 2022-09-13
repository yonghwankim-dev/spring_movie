package kr.yh.movie.service;

import kr.yh.movie.controller.LoginForm;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
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

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findOneByUserId(userId);

        if(member == null){
            throw new UsernameNotFoundException("회원이 존재하지 않습니다.");
        }

        return member;
    }

    public void login(LoginForm loginForm){
        Member member = memberRepository.findOneByUserId(loginForm.getUserId());
        System.out.println(member);
    }

    // 로그아웃
    public void logout(){

    }




}
