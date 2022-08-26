package kr.yh.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private ScreenSeatStatus status;

    @OneToOne(fetch = FetchType.LAZY)
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
        if(this.screen != null){
            this.screen.getScreenSeats().remove(this);
        }
        this.screen = screen;
        screen.getScreenSeats().add(this);
    }

    public void setSeat(Seat seat){
        if(this.seat != null){
            this.seat.getScreenSeats().remove(this);
        }
        this.seat = seat;
        seat.getScreenSeats().add(this);
    }

}
