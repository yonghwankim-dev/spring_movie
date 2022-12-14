package kr.yh.movie.domain;

import kr.yh.movie.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static kr.yh.movie.domain.PriceForPerson.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(name = "reserved_price")
    private int reservedPrice;

    @Embedded
    private Person person;

    @Column(name = "reserved_datetime")
    private LocalDateTime reservedDateTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<ScreenSeat> screenSeats = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //== 연관 관계 메서드 ==//
    public void setMember(Member member){
        if(this.member.getReservations().contains(this)){
            this.member.getReservations().remove(this);
        }
        this.member = member;
        member.getReservations().add(this);
    }

    public void setScreenSeats(ScreenSeat[] screenSeats){
        for(ScreenSeat screenSeat : screenSeats){
            if(this.screenSeats.contains(screenSeat)){
                this.screenSeats.remove(screenSeat);
            }
            this.screenSeats.add(screenSeat);
            screenSeat.setReservation(this);
        }
    }

    //== 생성 메서드 ==//
    public static Reservation createReservation(Member member, Person person, ScreenSeat... screenSeat){
        Reservation reservation = Reservation.builder()
                                             .person(person)
                                             .reservedPrice(getTotalPriceForPerson(person))
                                             .reservedDateTime(LocalDateTime.now())
                                             .status(ReservationStatus.RESERVED)
                                             .screenSeats(new ArrayList<>())
                                             .build();

        reservation.setMember(member);
        reservation.setScreenSeats(screenSeat);
        return reservation;
    }

    //== 계산 로직 ==//
    public static int getTotalPriceForPerson(Person person){
        return person.getTeenager() * TEENAGER.getPrice() +
               person.getAdult() * ADULT.getPrice() +
               person.getSenior() * SENIOR.getPrice();
    }

    //== 비즈니스 로직 ==//
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
    private boolean isExpiredReservation(){
        return status == ReservationStatus.EXPIRED;
    }
    private boolean isCanceledReservation(){
        return status == ReservationStatus.CANCEL;
    }
}
