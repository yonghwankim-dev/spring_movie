package kr.yh.movie.security;

import kr.yh.movie.domain.member.handler.AuthFailureHandler;
import kr.yh.movie.domain.member.handler.AuthSuccessHandler;
import kr.yh.movie.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.sql.DataSource;


@EnableWebSecurity
// @EnableGlobalMethodSecurity : 특정 페이지에 권한이 있는 유저만 접근을 허용하는 경우 권한 및 인증을 미리 체크하겠다는 설정을 활성화한다.
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
@Log
public class SecurityConfig {
    private final DataSource dataSource;
    private final SecurityUserService securityUserService;
    private final AuthSuccessHandler authSuccessHandler;
    private final AuthFailureHandler authFailureHandler;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests() // 권한요청 처리 설정 메서드
            .antMatchers("/**").permitAll()
            .antMatchers("/login").permitAll()
            .antMatchers("/members/**").permitAll()
            .antMatchers("/h2-console/**").permitAll()
            .anyRequest().authenticated();

        http.formLogin()
            .loginPage("/login")
            .usernameParameter("userId")
            .passwordParameter("password")
            .successHandler(authSuccessHandler)
            .failureHandler(authFailureHandler)
            .permitAll();

        http.sessionManagement()
            .maximumSessions(1)
            .maxSessionsPreventsLogin(false) // true면 중복 로그인을 막고, false면 이전 로그인의 세션을 해제
            .expiredUrl("/login?error=true&exception=세션이 만료된 계정에 로그인을 시도하였습니다.");

        http.logout()
            .logoutSuccessUrl("/login?logout")
            .deleteCookies("JSESSIONID", "remember-me")
            .invalidateHttpSession(true)
            .permitAll();

        http.rememberMe()
            .key("yh")
            .userDetailsService(securityUserService)
            .tokenRepository(getJDBCRepository())
            .tokenValiditySeconds(60*60*24);

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

    private PersistentTokenRepository getJDBCRepository(){
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }
}
