package kr.yh.movie.domain.member;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "member_role")
@EqualsAndHashCode(of = "member_role_id")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_role_id")
    private Long id;
    private String roleName;
}

