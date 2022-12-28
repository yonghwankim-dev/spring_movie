package kr.yh.movie.domain;

import kr.yh.movie.controller.screenSeat.ScreenSeatForm;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "screen_seat")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screen_seat_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private ScreenSeatStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    //== 연관 관계 메서드 ==//
    public void setReservation(Reservation reservation){
        this.reservation = reservation;
    }

    public void setScreen(Screen screen){
        this.screen = screen;
        screen.getScreenSeats().add(this);
    }

    public void setSeat(Seat seat){
        this.seat = seat;
        seat.getScreenSeats().add(this);
    }

    //== 생성 로직 ==//
    public static ScreenSeat createScreenSeat(ScreenSeatForm form){
        ScreenSeat screenSeat = ScreenSeat.builder()
                                          .id(form.getId())
                                          .status(form.getStatus())
                                          .build();

        screenSeat.setReservation(form.getReservation());
        screenSeat.setScreen(form.getScreen());
        screenSeat.setSeat(form.getSeat());
        return screenSeat;
    }

    //== 수정 로직 ==//
    public void changeInfo(ScreenSeatForm form){
        this.status      = form.getStatus();
        this.reservation = form.getReservation();
        this.screen      = form.getScreen();
        this.seat        = form.getSeat();
    }

    //== 비즈니스 로직 ==//
    // 상영좌석취소
    public void cancel(){
        this.status = ScreenSeatStatus.EMPTY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScreenSeat)) return false;
        ScreenSeat that = (ScreenSeat) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
