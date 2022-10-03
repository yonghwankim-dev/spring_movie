package kr.yh.movie.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.QSeat;
import kr.yh.movie.domain.Seat;
import kr.yh.movie.domain.Theater;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface SeatRepository extends CrudRepository<Seat, Long>, QuerydslPredicateExecutor<Seat> {

    default Predicate makePredicates(String type, String keyword, Theater theater){
        BooleanBuilder builder = new BooleanBuilder();
        QSeat seat = QSeat.seat;

        // type if ~ else

        builder.and(seat.theater.eq(theater));
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
}
