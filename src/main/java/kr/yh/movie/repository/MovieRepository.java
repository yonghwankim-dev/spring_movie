package kr.yh.movie.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.Movie;
import kr.yh.movie.domain.QMovie;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.domain.member.QMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long>, QuerydslPredicateExecutor<Movie> {
    default Predicate makePredicates(String type, String keyword){
        BooleanBuilder builder = new BooleanBuilder();
        QMovie movie = QMovie.movie;

        // type if ~ else

        // id > 0
        builder.and(movie.id.gt(0));

        if(type == null){
            return builder;
        }

        if(type.equals("name")){
            builder.and(movie.name.like("%"+keyword+"%"));
        }else if(type.equals("filmRating")){
            builder.and(movie.filmRating.like("%"+keyword+"%"));
        }

        return builder;
    }

    @Query("SELECT DISTINCT m FROM Movie m INNER JOIN Screen s ON m.id = s.movie.id " +
            "INNER JOIN Theater t ON s.theater.id = t.id " +
            "INNER JOIN Cinema c ON t.cinema.id = c.id " +
            "WHERE c.id = :cinemaId")
    List<Movie> findAllByCinemaId(@Param("cinemaId") Long cinemaId);

}
