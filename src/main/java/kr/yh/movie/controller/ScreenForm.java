package kr.yh.movie.controller;

import kr.yh.movie.domain.Movie;
import kr.yh.movie.domain.Screen;
import kr.yh.movie.domain.Seat;
import kr.yh.movie.domain.Theater;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ScreenForm {
    private Long id;
    @NotEmpty(message = "시작시간을 입력해주세요")
    private LocalDateTime startDateTime;
    @Min(value = 1, message = "회차를 입력해주세요")
    private int round;
    @NotEmpty(message = "영화를 선택해주세요")
    private Movie movie;
    @NotEmpty(message = "상영관을 선택해주세요")
    private Theater theater;

    public ScreenForm(Screen screen){
        this.id             = screen.getId();
        this.startDateTime  = screen.getStartDateTime();
        this.round          = screen.getRound();
        this.movie          = screen.getMovie();
        this.theater        = screen.getTheater();
    }
}
