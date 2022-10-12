package kr.yh.movie.domain.member;

import kr.yh.movie.controller.MemberForm;
import kr.yh.movie.domain.Address;
import kr.yh.movie.domain.Reservation;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"reservations", "roles"})
@Builder
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;		// 회원이름

    private LocalDate birthday;	// 회원생년월일

    @Column(name = "phone", unique = true)
    private String phone;		// 회원핸드폰번호

    @Embedded
    private Address address;	// 회원주소

    @Column(name = "email", unique = true)
    private String email;		// 회원이메일

    @Column(name = "userId", unique = true)
    private String userId;	    // 회원아이디

    private String password;	// 회원비밀번호

    private String gender;		// 회원성별

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private final List<Reservation> reservations = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime regDate; // 생성 시각

    @UpdateTimestamp
    private LocalDateTime lastLoginTime; // 마지막 로그인 시각

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "member")
    private List<MemberRole> roles;



    //== 생성 로직 ==//
    public static Member createMember(MemberForm form){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Member member = Member.builder()
                              .name(form.getName())
                              .birthday(form.getBirthday())
                              .phone(form.getPhone())
                              .address(new Address(form.getZipcode(), form.getStreet(), form.getDetail()))
                              .email(form.getEmail())
                              .userId(form.getUserId())
                              .password(passwordEncoder.encode(form.getPassword()))
                              .gender(form.getGender())
                              .build();
        return member;
    }

    //== 수정 로직 ==//
    public void changeInfo(MemberForm form){
        this.name     = form.getName();
        this.phone    = form.getPhone();
        this.address.changeInfo(form.getZipcode(), form.getStreet(), form.getDetail());
        this.email    = form.getEmail();
        this.userId   = form.getUserId();
    }

    // 계정이 갖고 있는 권한 목록은 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(()->{
            return "계정별 등록할 권한";
        });
        return grantedAuthorities;
    }

    // 계정의 아이디를 리턴
    @Override
    public String getUsername() {
        return userId;
    }

    // 계정이 만료되지 않았는지 리턴(true : 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있지 않은지 리턴(true : 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지 리턴 (true : 만료 안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화(사용가능)인지 리턴 (true : 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
