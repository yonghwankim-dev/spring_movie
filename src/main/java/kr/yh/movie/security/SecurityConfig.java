package kr.yh.movie.security;

import kr.yh.movie.domain.member.handler.AuthFailureHandler;
import kr.yh.movie.domain.member.handler.AuthSuccessHandler;
import kr.yh.movie.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity
// @EnableGlobalMethodSecurity : 특정 페이지에 권한이 있는 유저만 접근을 허용하는 경우 권한 및 인증을 미리 체크하겠다는 설정을 활성화한다.
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final MemberService memberService;
    private final AuthSuccessHandler authSuccessHandler;
    private final AuthFailureHandler authFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests() // 권한요청 처리 설정 메서드
                .antMatchers("/**","/login","members/**", "/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST, "/members/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("userId")
                .passwordParameter("password")
                .loginProcessingUrl("/login")
                .successHandler(authSuccessHandler)
                .failureHandler(authFailureHandler)
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .permitAll()
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false) // true면 중복 로그인을 막고, false면 이전 로그인의 세션을 해제
                .expiredUrl("/login?error=true&exception=세션이 만료된 계정에 로그인을 시도하였습니다.")
                .and()
                .and().rememberMe()
                .alwaysRemember(false) // 항상 기억할 것인지 여부
                .tokenValiditySeconds(3600)
                .rememberMeParameter("remember-me")
                .and()
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/h2-console/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
