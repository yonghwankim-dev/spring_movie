package kr.yh.movie.domain.member.handler;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String message = "아이디 또는 비밀번호가 일치하지 않습니다.";

        // exception 관련 메시지 처리
        if(exception instanceof DisabledException){ // 계정 비활성화
            message = "계정이 비활성화되어 로그인이 불가능합니다.";
        }else if(exception instanceof CredentialsExpiredException){ // 비밀번호 만료
            message = "비밀번호가 만료되었습니다.";
        }else if(exception instanceof BadCredentialsException){ // 비밀번호 불일치
            message = "비밀번호가 불일치합니다.";
        }else if(exception instanceof UsernameNotFoundException){ // 계정 없음
            message = "계정이 존재하지 않습니다.";
        }else if(exception instanceof AccountExpiredException){ // 계정 만료
            message = "계정이 만료되었습니다.";
        }else if(exception instanceof LockedException){ // 계정 잠김
            message = "계정이 잠금되었습니다.";
        }

        message = URLEncoder.encode(message, "UTF-8");
        setDefaultFailureUrl("/login?error=true&exception="+message);

        super.onAuthenticationFailure(request, response, exception);
    }
}
