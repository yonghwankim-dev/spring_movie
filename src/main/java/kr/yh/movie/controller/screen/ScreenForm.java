package kr.yh.movie.controller.screen;

import kr.yh.movie.domain.Movie;
import kr.yh.movie.domain.Screen;
import kr.yh.movie.domain.Seat;
import kr.yh.movie.domain.Theater;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDateTime;
    @Min(value = 1, message = "회차를 입력해주세요")
    @Max(value = 100, message = "최대회차는 100까지입니다.")
    private int round;
    private Long movieId;
    private Long theaterId;

    public ScreenForm(Screen screen){
        this.id             = screen.getId();
        this.startDateTime  = screen.getStartDateTime();
        this.round          = screen.getRound();
        this.movieId        = screen.getMovie().getId();
        this.theaterId      = screen.getTheater().getId();
    }
}
