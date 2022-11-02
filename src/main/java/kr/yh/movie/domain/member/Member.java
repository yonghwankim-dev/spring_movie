package kr.yh.movie.domain.member;

import kr.yh.movie.controller.member.MemberDTO;
import kr.yh.movie.domain.Reservation;
import kr.yh.movie.util.ModelMapperUtils;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.config.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static kr.yh.movie.util.ModelMapperUtils.*;
import static org.modelmapper.config.Configuration.AccessLevel.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "member_id")
@ToString(exclude = {"reservations"})
@Builder
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String name;
    private LocalDate birthday;
    @Column(name = "phone", unique = true)
    private String phone;
    @Embedded
    private Address address;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "userId", unique = true)
    private String userId;
    private String password;
    private String gender;
    @Enumerated(EnumType.STRING)
    private MemberRole roleName;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private final List<Reservation> reservations = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime regDate;
    @UpdateTimestamp
    private LocalDateTime lastLoginTime;
    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;


    //== 생성 로직 ==//
    public static Member member(MemberDTO form){
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
                              .roleName(form.getRoleName())
                              .build();
        return member;
    }

    public static Member of(MemberDTO dto){
        ModelMapper modelMapper = getModelMapper();
        PropertyMap<MemberDTO, Member> addressMapping = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().getAddress().setZipcode(source.getZipcode());
                map().getAddress().setStreet(source.getStreet());
                map().getAddress().setDetail(source.getDetail());
            }
        };
        modelMapper.addMappings(addressMapping);
        return modelMapper.map(dto, Member.class);
    }

    //== 수정 로직 ==//
    public void changeInfo(MemberDTO form){
        this.id = form.getId();
        this.name = form.getName();
        this.birthday = form.getBirthday();
        this.phone = form.getPhone();
        this.address.changeInfo(form.getZipcode(), form.getStreet(), form.getDetail());
        this.email = form.getEmail();
        this.userId = form.getUserId();
        this.password = form.getPassword();
        this.gender = form.getGender();
        this.roleName = form.getRoleName();
    }

    // 계정이 갖고 있는 권한 목록은 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(()-> "계정별 등록할 권한");
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
