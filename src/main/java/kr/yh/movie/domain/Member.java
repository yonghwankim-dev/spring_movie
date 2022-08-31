package kr.yh.movie.domain;

import kr.yh.movie.controller.MemberForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String name;		// 회원이름
    private LocalDateTime birthday;	// 회원생년월일
    private String phone;		// 회원핸드폰번호
    @Embedded
    private Address address;	// 회원주소
    private String email;		// 회원이메일
    private String userId;	    // 회원아이디
    private String password;	// 회원비밀번호
    private String gender;		// 회원성별

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private final List<Reservation> reservations = new ArrayList<>();

    //== 생성 로직 ==//
    public static Member createMember(MemberForm form){
        Member member = Member.builder()
                              .name(form.getName())
                              .birthday(form.getBirthday())
                              .phone(form.getPhone())
                              .address(new Address(form.getCity(), form.getStreet(), form.getZipcode()))
                              .email(form.getEmail())
                              .userId(form.getUserId())
                              .password(form.getPassword())
                              .gender(form.getGender())
                              .build();

        return member;
    }

    //== 수정 로직 ==//
    public void changeInfo(Member member){
        this.name = member.name;
        this.birthday = member.birthday;
        this.phone = member.phone;
        this.address = member.address;
        this.email = member.email;
        this.userId = member.userId;
        this.password = member.password;
        this.gender = member.gender;
    }
}
