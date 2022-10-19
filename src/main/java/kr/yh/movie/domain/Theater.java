package kr.yh.movie.domain;

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
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theater_id")
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    private final List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    private final List<Screen> screens = new ArrayList<>();

    //== 연관 관계 메서드 ==//
    public void setCinema(Cinema cinema){
        this.cinema = cinema;
        cinema.getTheaters().add(this);
    }

    //== 생성 로직 ==//
    public static Theater createTheater(TheaterForm form){
        Theater theater = Theater.builder()
                                 .name(form.getName())
                                 .cinema(form.getCinema())
                                 .build();
        return theater;
    }

    //== 수정 로직 ==//
    public void changeInfo(TheaterForm form){
        this.name       = form.getName();
        this.cinema     = form.getCinema();
    }
}
