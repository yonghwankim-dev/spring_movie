package kr.yh.movie.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.QCinema;
import kr.yh.movie.domain.QTheater;
import kr.yh.movie.domain.Theater;
import kr.yh.movie.domain.member.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TheaterRepository extends CrudRepository<Theater, Long>, QuerydslPredicateExecutor<Theater> {
    @Query("SELECT t FROM Theater t WHERE t.cinema.id = :cinemaId")
    List<Theater> findAllByCinemaId(@Param("cinemaId") Long cinemaId);

    default Predicate makePredicates(String type, String keyword, Long cinemaId){
        BooleanBuilder builder = new BooleanBuilder();
        QTheater theater = QTheater.theater;

        // type if ~ else
        builder.and(theater.cinema.id.eq(cinemaId));
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
