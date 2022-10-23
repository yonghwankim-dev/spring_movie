package kr.yh.movie.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.QCinema;
import kr.yh.movie.domain.QReservation;
import kr.yh.movie.domain.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReservationRepository extends CrudRepository<Reservation, Long>, QuerydslPredicateExecutor<Reservation> {
    default Predicate makePredicates(String type, String keyword, Long cinemaId){
        BooleanBuilder builder = new BooleanBuilder();
        QReservation reservation = QReservation.reservation;

        // type if ~ else

        // id > 0
        builder.and(reservation.id.gt(0));

        if(type == null){
            return builder;
        }

        return builder;
    }

    @Query("SELECT r FROM Reservation r WHERE r.member.id = :memberId")
    Optional<Reservation> findByMemberId(@Param("memberId") Long memberId);
}
