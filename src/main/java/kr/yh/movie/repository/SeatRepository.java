package kr.yh.movie.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.QSeat;
import kr.yh.movie.domain.QTheater;
import kr.yh.movie.domain.Seat;
import kr.yh.movie.domain.Theater;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SeatRepository extends CrudRepository<Seat, Long>{

    @Query("SELECT s FROM Seat s WHERE s.theater.id = :theater_id ORDER BY s.seat_row, s.seat_col")
    List<Seat> findAllByTheaterId(@Param("theater_id") long theater_id);

    @Query("DELETE FROM Seat s WHERE s.theater.id = :theater_id")
    @Transactional
    @Modifying
    void deleteAllByTheaterId(@Param("theater_id") long theater_id);

    @Query("SELECT s.seat_col FROM Seat s WHERE s.theater.id = :theater_id GROUP BY s.seat_col")
    List<String> getSeatColsByTheaterId(@Param("theater_id") long theater_id);

    @Query("SELECT s.seat_row FROM Seat s WHERE s.theater.id = :theater_id GROUP BY s.seat_row")
    List<String> getSeatRowsByTheaterId(@Param("theater_id") long theater_id);
}
