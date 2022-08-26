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
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theater_id")
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @OneToMany(mappedBy = "theater")
    private final List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "theater")
    private final List<Screen> screens = new ArrayList<>();

    //== 연관 관계 메서드 ==//
    public void setCinema(Cinema cinema){
        if(this.cinema != null){
            this.cinema.getTheaters().remove(this);
        }
        this.cinema = cinema;
        cinema.getTheaters().add(this);
    }
}
