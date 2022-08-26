package kr.yh.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Enumerated(EnumType.STRING)
    private ReservationStatus status; // 예매상태
    
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<ScreenSeat> screenSeats = new ArrayList<>(); // 상영좌석정보들

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

    //== 생성 메서드 ==//
    public static Reservation createReservation(Member member, ScreenSeat screenSeat, int reservedPrice, Person person){
        Reservation reservation = Reservation.builder()
                                             .reservedPrice(reservedPrice)
                                             .reservedDateTime(LocalDateTime.now())
                                             .person(person)
                                             .build();

        reservation.setMember(member);
        return reservation;
    }

    //== 비즈니스 로직 ==//
    // 예매취소
    public void cancel(){
        if(isExpiredReservation()){
            throw new IllegalStateException("이미 만료된 예매입니다.");
        }
        if(isCanceledReservation()){
            throw new IllegalStateException("이미 취소된 예매입니다.");
        }
        this.status = ReservationStatus.CANCEL;
        for(ScreenSeat screenSeat : screenSeats){
            screenSeat.cancel();
        }
    }

    //== 조회 로직 ==//
    // 예매가 만료되었는지 조회
    private boolean isExpiredReservation(){
        return status == ReservationStatus.EXPIRED;
    }
    // 예매가 취소되었는지 조회
    private boolean isCanceledReservation(){
        return status == ReservationStatus.CANCEL;
    }
}
