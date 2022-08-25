package kr.yh.movie.domain;

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
    private String contact;		// 회원핸드폰번호
    @Embedded
    private Address address;	// 회원주소
    private String email;		// 회원이메일
    private String userId;	    // 회원아이디
    private String password;	// 회원비밀번호
    private String gender;		// 회원성별

//    @OneToMany(mappedBy = "member")
//    private final List<Book> books = new ArrayList<>();
}
