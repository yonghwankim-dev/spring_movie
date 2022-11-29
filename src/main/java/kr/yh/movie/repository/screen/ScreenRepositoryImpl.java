package kr.yh.movie.repository.screen;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.yh.movie.domain.*;
import kr.yh.movie.repository.cinema.CinemaPredicate;
import kr.yh.movie.repository.movie.MoviePredicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Log
public class ScreenRepositoryImpl {
    private final JPAQueryFactory factory;

    public List<Screen> findAll(String location, LocalDateTime startDate){
        return findAll(location, null, startDate);
    }

    public List<Screen> findAll(String location, Long cinemaId, LocalDateTime startDate){
        return findAll(location, cinemaId, null, startDate);
    }

    public List<Screen> findAll(String location, Long cinemaId, Long movieId, LocalDateTime startDate){
        location = location == null ? "서울" : location;
        cinemaId = cinemaId == null ? 0L : cinemaId;
        startDate = startDate == null ? LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT) : startDate;

        QCinema cinema = QCinema.cinema;
        QTheater theater = QTheater.theater;
        QScreen screen = QScreen.screen;
        QMovie movie = QMovie.movie;

        Predicate moviePredicate = MoviePredicate.search(movieId);
        Predicate cinemaPredicate = CinemaPredicate.search(location, cinemaId);
        Predicate screenPredicate = ScreenPredicate.search(startDate);

        List<Screen> result = factory.select(screen)
                                    .from(screen)
                                    .innerJoin(movie).on(screen.movie.id.eq(movie.id))
                                    .innerJoin(theater).on(screen.theater.id.eq(theater.id))
                                    .innerJoin(cinema).on(theater.cinema.id.eq(cinema.id))
                                    .where(moviePredicate, cinemaPredicate, screenPredicate)
                                    .orderBy(movie.name.asc(), screen.startDateTime.asc())
                                    .fetch();
        return result;
    }
}
