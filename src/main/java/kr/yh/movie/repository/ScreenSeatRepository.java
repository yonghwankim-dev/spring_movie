package kr.yh.movie.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.QScreenSeat;
import kr.yh.movie.domain.ReservationStatus;
import kr.yh.movie.domain.ScreenSeat;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ScreenSeatRepository extends CrudRepository<ScreenSeat, Long>, QuerydslPredicateExecutor<ScreenSeat> {
    default Predicate makePredicates(String type, String keyword){
        BooleanBuilder builder = new BooleanBuilder();
        QScreenSeat screenSeat = QScreenSeat.screenSeat;

        // type if ~ else

        // id > 0
        builder.and(screenSeat.id.gt(0));

        if(type == null){
            return builder;
        }

        if(type.equals("reservation")){
            builder.and(screenSeat.reservation.status.eq(ReservationStatus.valueOf(keyword)));
        }

        return builder;
    }
}
