package kr.yh.movie.domain;

import kr.yh.movie.controller.seat.SeatForm;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"screenSeats", "theater"})
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;
    private String seat_row;
    private String seat_col;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    private List<ScreenSeat> screenSeats = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id")
    private Theater theater;

    //== 연관 관계 메서드 ==//
    public void setTheater(Theater theater){
        this.theater = theater;
        theater.getSeats().add(this);
    }

    //== 생성 로직 ==//
    public static Seat createSeat(SeatForm form, Theater theater){
        Seat seat = Seat.builder()
                        .seat_row(form.getSeat_row())
                        .seat_col(form.getSeat_col())
                        .build();
        seat.setTheater(theater);
        return seat;
    }

    //== 수정 로직 ==//
    public void changeInfo(SeatForm form, Theater theater){
        this.seat_row = form.getSeat_row();
        this.seat_col = form.getSeat_col();
        setTheater(theater);
    }
}
