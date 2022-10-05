package kr.yh.movie.domain;

import kr.yh.movie.controller.SeatForm;
import kr.yh.movie.controller.TheaterForm;
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
        if(this.theater != null){
            this.theater.getSeats().remove(this);
        }
        this.theater = theater;
        theater.getSeats().add(this);
    }

    //== 생성 로직 ==//
    public static Seat createSeat(SeatForm form){
        Seat seat = Seat.builder()
                        .seat_row(form.getSeat_row())
                        .seat_col(form.getSeat_col())
                        .build();
        return seat;
    }
}
