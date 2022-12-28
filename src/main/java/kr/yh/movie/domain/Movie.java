package kr.yh.movie.domain;

import kr.yh.movie.controller.movie.MovieDTO;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    @ToString.Exclude
    private final List<Screen> screens = new ArrayList<>();

    //== 생성 로직 ==//
    public static Movie createMovie(MovieDTO form){
        return Movie.builder()
                    .name(form.getName())
                    .filmRating(form.getFilmRating())
                    .runtime(form.getRuntime())
                    .build();
    }

    //== 수정 로직 ==//
    public void changeInfo(MovieDTO form){
        this.name       = form.getName();
        this.filmRating = form.getFilmRating();
        this.runtime    = form.getRuntime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Movie movie = (Movie) o;
        return id != null && Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
