package kr.yh.movie.domain;

import kr.yh.movie.controller.screen.ScreenForm;
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
        if(movie.getScreens().contains(this)){
            movie.getScreens().remove(this);
        }
        movie.getScreens().add(this);
    }

    public void setTheater(Theater theater){
        this.theater = theater;
        if(theater.getScreens().contains(this)){
            theater.getScreens().remove(this);
        }
        theater.getScreens().add(this);
    }

    //== 생성 로직 ==//
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
    public void changeInfo(ScreenForm form, Movie movie, Theater theater){
        this.startDateTime = form.getStartDateTime();
        this.round = form.getRound();
        setMovie(movie);
        setTheater(theater);
    }
}
