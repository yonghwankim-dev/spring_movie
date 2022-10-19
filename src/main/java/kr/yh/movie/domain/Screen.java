package kr.yh.movie.domain;

import kr.yh.movie.controller.CinemaForm;
import kr.yh.movie.controller.ScreenForm;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "screenSeats")
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screen_id")
    private Long id;                       // 상영번호

    @Column(name = "start_datetime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDateTime;   // 상영시작시간

    @Min(value = 1)
    @Max(value = 100)
    private int round;                      // 상영회차

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL)
    private List<ScreenSeat> screenSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id")
    private Theater theater;

    //== 연관 관계 메서드 ==//
    public void setMovie(Movie movie){
        this.movie = movie;
        movie.getScreens().add(this);
    }

    public void setTheater(Theater theater){
        this.theater = theater;
        theater.getScreens().add(this);
    }

    //== 생성 로직 ==//
    public static Screen createScreen(ScreenForm form){
        Screen screen = Screen.builder()
                .startDateTime(form.getStartDateTime())
                .movie(form.getMovie())
                .theater(form.getTheater())
                .round(form.getRound())
                .build();
        return screen;
    }

    public static Screen createScreen(LocalDateTime startDateTime, int round, Movie movie, Theater theater){
        Screen screen = Screen.builder()
                              .startDateTime(LocalDateTime.now())
                              .round(round)
                              .screenSeats(new ArrayList<>())
                              .build();
        screen.setMovie(movie);
        screen.setTheater(theater);
        return screen;
    }

    //== 수정 로직 ==//
    public void changeInfo(ScreenForm form){
        this.startDateTime  = form.getStartDateTime();
        this.movie          = form.getMovie();
        this.theater        = form.getTheater();
        this.round          = form.getRound();
    }
}
