package kr.yh.movie.controller.movie;

import kr.yh.movie.controller.cinema.CinemaDTO;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.Movie;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import static kr.yh.movie.util.ModelMapperUtils.getModelMapper;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovieDTO {
    private Long    id;
    @NotEmpty(message = "영화제목을 입력해주세요.")
    private String  name;
    private int     filmRating;
    @Min(value = 1, message = "상영시간은 0보다 커야합니다.")
    private int     runtime;

    public static MovieDTO createMovieDTO(){
        return new MovieDTO();
    }

    public static MovieDTO of(Movie movie){
        return getModelMapper().map(movie, MovieDTO.class);
    }
}
