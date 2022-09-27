package kr.yh.movie.controller;

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
public class MovieForm {
    private Long    id;
    @NotEmpty(message = "영화제목을 입력해주세요.")
    private String  name;
    private int     filmRating;
    @NotEmpty(message = "상영시간을 입력해주세요")
    private int     runtime;

    public MovieForm(Movie movie){
        this.id         = movie.getId();
        this.name       = movie.getName();
        this.filmRating = movie.getFilmRating();
        this.runtime    = movie.getRuntime();
    }
}
