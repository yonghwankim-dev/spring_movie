package kr.yh.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;          // 예매번호
    @Column(name = "reserved_price")
    private int reservedPrice; // 예매가격
    @Embedded
    private Person person;    // 예매 인원정보
    @Column(name = "reserved_datetime")
    private LocalDateTime reservedDateTime; // 예매시간

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "screen_seat_id")
    private ScreenSeat screenSeat;      // 상영좌석정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;    // 회원정보



    //== 연관 관계 메서드 ==//
    public void setMember(Member member){
        if(this.member != null){ // 기존 회원이 존재하는 경우
            this.member.getReservations().remove(this);
        }
        this.member = member;
        member.getReservations().add(this);
    }

    public void setScreenSeat(ScreenSeat screenSeat){
        this.screenSeat = screenSeat;
        screenSeat.setReservation(this);
    }

    //== 생성 메서드 ==//

    //== 비즈니스 로직 ==//
    //== 조회 로직 ==//

}
