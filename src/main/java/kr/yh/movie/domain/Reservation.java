package kr.yh.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reserve")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserve_id")
    private Long id;          // 예매번호
    @Column(name = "reserve_price")
    private int reservePrice; // 예매가격
    @Embedded
    private Person person;    // 예매 인원정보
    @Column(name = "reserve_datetime")
    private LocalDateTime reserveDateTime; // 예매시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;    // 회원정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen")
    private Screen screen;      // 상영정보
}
