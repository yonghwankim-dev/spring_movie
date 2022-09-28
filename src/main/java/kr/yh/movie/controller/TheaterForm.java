package kr.yh.movie.controller;

import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.Theater;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TheaterForm {
    private Long    id;
    @NotEmpty(message = "상영관 이름을 입력해주세요")
    private String  name;
    private Cinema cinema;

    public TheaterForm(Theater theater) {
        this.id     = theater.getId();
        this.name   = theater.getName();
        this.cinema = theater.getCinema();
    }
}
