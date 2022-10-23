package kr.yh.movie.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.QScreenSeat;
import kr.yh.movie.domain.ReservationStatus;
import kr.yh.movie.domain.ScreenSeat;
import kr.yh.movie.domain.ScreenSeatStatus;
import kr.yh.movie.domain.member.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScreenSeatRepository extends CrudRepository<ScreenSeat, Long>, QuerydslPredicateExecutor<ScreenSeat> {
    default Predicate makePredicates(String type, String keyword, Long screenId){
        BooleanBuilder builder = new BooleanBuilder();
        QScreenSeat screenSeat = QScreenSeat.screenSeat;

        // type if ~ else

        builder.and(screenSeat.screen.id.eq(screenId));
        // id > 0
        builder.and(screenSeat.id.gt(0));

        if(type == null){
            return builder;
        }

        if(type.equals("status")){
            builder.and(screenSeat.status.eq(ScreenSeatStatus.valueOf(keyword)));
        }

        return builder;
    }

    @Query("SELECT s FROM ScreenSeat s WHERE s.screen.id = :screenId")
    List<ScreenSeat> findAllByScreenId(@Param("screenId") Long screenId);

    @Query("SELECT s FROM ScreenSeat s WHERE s.screen.id = :screenId AND s.id = (SELECT MIN(id) FROM ScreenSeat)")
    ScreenSeat findFirstByScreenId(@Param("screenId") Long screenId);
}
