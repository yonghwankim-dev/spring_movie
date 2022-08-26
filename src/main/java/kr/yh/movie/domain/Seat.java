package kr.yh.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;
    private String row;
    private String col;

    @OneToMany(mappedBy = "seat")
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
}
