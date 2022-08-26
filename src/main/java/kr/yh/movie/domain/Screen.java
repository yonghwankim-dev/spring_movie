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
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screen_id")
    private Long id;                       // 상영번호
    @Column(name = "start_datetime")
    private LocalDateTime startDateTime;   // 상영시작시간
    private int round;                      // 상영회차

    @OneToMany(mappedBy = "screen")
    private List<ScreenSeat> screenSeats = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id")
    private Theater theater;

    //== 연관 관계 메서드 ==//
    public void setMovie(Movie movie){
        if(this.movie != null){
            this.movie.getScreens().remove(this);
        }
        this.movie = movie;
        movie.getScreens().add(this);
    }

    public void setTheater(Theater theater){
        if(this.theater != null){
            this.theater.getScreens().remove(this);
        }
        this.theater = theater;
        theater.getScreens().add(this);
    }
}
