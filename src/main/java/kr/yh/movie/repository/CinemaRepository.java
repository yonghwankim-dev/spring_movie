package kr.yh.movie.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.controller.cinema.CinemaLocationDTO;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.Movie;
import kr.yh.movie.domain.QCinema;
import kr.yh.movie.domain.QMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CinemaRepository  extends JpaRepository<Cinema, Long>, QuerydslPredicateExecutor<Cinema> {
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

    @Query(value = "select new kr.yh.movie.controller.cinema.CinemaLocationDTO(c.location, count(c))" +
            "from Cinema c " +
            "group by c.location " +
            "order by case when c.location = '서울' then 1 " +
            "when c.location = '경기/인천' then 2 " +
            "when c.location = '충청/대전' then 3 " +
            "when c.location = '전라/광주' then 4 " +
            "when c.location = '경북/대구' then 5 " +
            "when c.location = '경남/부산/울산' then 6 " +
            "when c.location = '강원' then 7 " +
            "else 8 end")
    List<CinemaLocationDTO> findAllLocationAndCountGroupByLocation();
}
