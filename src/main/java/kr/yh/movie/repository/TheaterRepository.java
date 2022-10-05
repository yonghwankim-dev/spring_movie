package kr.yh.movie.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.QCinema;
import kr.yh.movie.domain.QTheater;
import kr.yh.movie.domain.Theater;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface TheaterRepository extends CrudRepository<Theater, Long>, QuerydslPredicateExecutor<Theater> {
    default Predicate makePredicates(String type, String keyword, Cinema cinema){
        BooleanBuilder builder = new BooleanBuilder();
        QTheater theater = QTheater.theater;

        // type if ~ else
        builder.and(theater.cinema.eq(cinema));
        // id > 0
        builder.and(theater.id.gt(0));

        if(type == null){
            return builder;
        }

        if(type.equals("name")){
            builder.and(theater.name.like("%"+keyword+"%"));
        }

        return builder;
    }
}
