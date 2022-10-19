package kr.yh.movie.domain;

import kr.yh.movie.controller.MovieForm;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"screens"})
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;
    private String name;
    @Column(name = "film_rating")
    private int filmRating;
    private int runtime;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private final List<Screen> screens = new ArrayList<>();

    //== 생성 로직 ==//
    public static Movie createMovie(MovieForm form){
        Movie movie = Movie.builder()
                            .name(form.getName())
                            .filmRating(form.getFilmRating())
                            .runtime(form.getRuntime())
                            .build();
        return movie;
    }

    //== 수정 로직 ==//
    public void changeInfo(MovieForm form){
        this.name       = form.getName();
        this.filmRating = form.getFilmRating();
        this.runtime    = form.getRuntime();
    }
}
