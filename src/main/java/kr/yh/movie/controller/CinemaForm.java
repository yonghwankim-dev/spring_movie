package kr.yh.movie.controller;

import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CinemaForm {
    private Long id;
    @NotEmpty(message = "영화관 이름을 입력해주세요")
    private String name;
    private String location;

    public CinemaForm(Cinema cinema){
        this.id         = cinema.getId();
        this.name       = cinema.getName();
        this.location   = cinema.getLocation();
    }
}
