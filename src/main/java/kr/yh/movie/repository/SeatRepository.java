package kr.yh.movie.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.QSeat;
import kr.yh.movie.domain.Seat;
import kr.yh.movie.domain.Theater;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeatRepository extends CrudRepository<Seat, Long>, QuerydslPredicateExecutor<Seat> {

    default Predicate makePredicates(String type, String keyword, Long theaterId){
        BooleanBuilder builder = new BooleanBuilder();
        QSeat seat = QSeat.seat;

        // type if ~ else

        builder.and(seat.theater.id.eq(theaterId));
        // id > 0
        builder.and(seat.id.gt(0));

        if(type == null){
            return builder;
        }

        if(type.equals("seat_row")){
            builder.and(seat.seat_row.like("%"+keyword+"%"));
        }
        if(type.equals("seat_col")){
            builder.and(seat.seat_col.like("%"+keyword+"%"));
        }

        return builder;
    }

    @Query("SELECT s FROM Seat s WHERE s.theater.id = :theaterId AND s.id > 0")
    List<Seat> findAllByTheaterId(@Param("theaterId") Long theaterId);
}
