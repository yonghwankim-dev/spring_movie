package kr.yh.movie.repository.movie;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.yh.movie.domain.*;
import kr.yh.movie.repository.cinema.CinemaPredicate;
import kr.yh.movie.repository.screen.ScreenPredicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MovieRepositoryImpl{
    private final JPAQueryFactory factory;

    public List<Movie> findAllMovieOnScreen(String location, LocalDateTime startDate){
        return findAllMovieOnScreen(location, startDate, (Long) null);
    }

    public List<Movie> findAllMovieOnScreen(String location, LocalDateTime startDate, Long cinemaId){
        return findAllMovieOnScreen(location, startDate, toCinemaIdList(cinemaId));
    }

    public List<Movie> findAllMovieOnScreen(String location, LocalDateTime startDate, Long cinemaId, Long movieId){
        return findAllMovieOnScreen(location, startDate, toCinemaIdList(cinemaId), movieId);
    }

    public List<Movie> findAllMovieOnScreen(String location, LocalDateTime startDate, List<Long> cinemaIdList){
        return findAllMovieOnScreen(location, startDate, cinemaIdList, null);
    }

    public List<Movie> findAllMovieOnScreen(String location, LocalDateTime startDate, List<Long> cinemaIdList, Long movieId){
        QScreen screen = QScreen.screen;
        QMovie movie = QMovie.movie;
        QTheater theater = QTheater.theater;
        QCinema cinema = QCinema.cinema;
        Predicate cinemaSearch = CinemaPredicate.search(location, cinemaIdList);
        Predicate movieSearch = MoviePredicate.search(movieId);
        Predicate screenSearch = ScreenPredicate.search(startDate);

        List<Movie> result = factory.select(movie)
                                    .distinct()
                                    .from(screen)
                                    .innerJoin(movie).on(movie.id.eq(screen.movie.id))
                                    .innerJoin(theater).on(theater.id.eq(screen.theater.id))
                                    .innerJoin(cinema).on(theater.cinema.id.eq(cinema.id))
                                    .where(cinemaSearch, movieSearch, screenSearch)
                                    .fetch();

        return result;
    }

    private List<Long> toCinemaIdList(Long... cinemaId){
        if(cinemaId == null){
            return new ArrayList<>();
        }
        return Arrays.stream(cinemaId).filter(id->id != null).collect(Collectors.toList());
    }
}
