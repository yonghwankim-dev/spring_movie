package kr.yh.movie.domain.member;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "member_role")
@EqualsAndHashCode(of = "member_role_id")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_role_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private MemberRoleName roleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //== 연관 관계 메서드 ==//
    public void setMember(Member member){
        this.member = member;
        member.getRoles().add(this);
    }
}

