package kr.yh.movie.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.Movie;
import kr.yh.movie.domain.QCinema;
import kr.yh.movie.domain.QMovie;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CinemaRepository  extends CrudRepository<Cinema, Long>, QuerydslPredicateExecutor<Cinema> {
    default Predicate makePredicates(String type, String keyword){
        BooleanBuilder builder = new BooleanBuilder();
        QCinema cinema = QCinema.cinema;

        // type if ~ else

        // id > 0
        builder.and(cinema.id.gt(0));

        if(type == null){
            return builder;
        }

        if(type.equals("name")){
            builder.and(cinema.name.like("%"+keyword+"%"));
        }else if(type.equals("location")){
            builder.and(cinema.location.like("%"+keyword+"%"));
        }

        return builder;
    }
}
