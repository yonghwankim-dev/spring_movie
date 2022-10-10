package kr.yh.movie.controller;

import kr.yh.movie.domain.*;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ScreenSeatForm {
    private Long id;
    @Enumerated(EnumType.STRING)
    private ScreenSeatStatus status;
    private Reservation reservation;
    private Screen screen;
    private Seat seat;

    public ScreenSeatForm(ScreenSeat screenSeat){
        this.id          = screenSeat.getId();
        this.status      = screenSeat.getStatus();
        this.reservation = screenSeat.getReservation();
        this.screen      = screenSeat.getScreen();
        this.seat        = screenSeat.getSeat();
    }
}
