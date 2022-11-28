package kr.yh.movie.repository.cinema;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.yh.movie.domain.*;
import kr.yh.movie.repository.movie.MoviePredicate;
import kr.yh.movie.repository.screen.ScreenPredicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Log
public class CinemaRepositoryImpl {
    private final JPAQueryFactory factory;

    public List<Cinema> findAll(String location, LocalDateTime startDate){
        return findAll(location, startDate, null);
    }

    public List<Cinema> findAll(String location, LocalDateTime startDate, Long cinemaId) {
        return findAll(location, startDate, cinemaId, null);
    }

    public List<Cinema> findAll(String location, LocalDateTime startDate, Long cinemaId, Long movieId){
        QCinema cinema = QCinema.cinema;
        QTheater theater = QTheater.theater;
        QScreen screen = QScreen.screen;
        QMovie movie = QMovie.movie;

        Predicate moviePredicate = MoviePredicate.search(movieId);
        Predicate cinemaPredicate = CinemaPredicate.search(location, cinemaId);
        Predicate screenPredicate = ScreenPredicate.search(startDate);

        List<Cinema> result = factory.select(cinema)
                                     .distinct()
                                     .from(cinema)
                                     .innerJoin(theater).on(cinema.id.eq(theater.cinema.id))
                                     .innerJoin(screen).on(theater.id.eq(screen.theater.id))
                                     .innerJoin(movie).on(movie.id.eq(screen.movie.id))
                                     .where(cinemaPredicate, moviePredicate, screenPredicate)
                                     .fetch();
        return result;
    }

}
