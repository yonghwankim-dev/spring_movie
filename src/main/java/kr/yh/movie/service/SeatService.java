package kr.yh.movie.service;

import kr.yh.movie.domain.Seat;
import kr.yh.movie.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log
public class SeatService {
    private final SeatRepository repo;

    @Transactional
    public <S extends Seat> S save(S entity) {
        return repo.save(entity);
    }

    @Transactional
    public <S extends Seat> Iterable<S> saveAll(Iterable<S> entities) {
        return repo.saveAll(entities);
    }

    public Optional<Seat> findById(Long aLong) {
        return repo.findById(aLong);
    }

    public boolean existsById(Long aLong) {
        return repo.existsById(aLong);
    }

    public Iterable<Seat> findAll() {
        return repo.findAll();
    }

    public Iterable<Seat> findAllById(Iterable<Long> longs) {
        return repo.findAllById(longs);
    }

    public long count() {
        return repo.count();
    }

    @Transactional
    public void deleteById(Long aLong) {
        repo.deleteById(aLong);
    }

    @Transactional
    public void delete(Seat entity) {
        repo.delete(entity);
    }

    @Transactional
    public void deleteAllById(Iterable<? extends Long> longs) {
        repo.deleteAllById(longs);
    }

    @Transactional
    public void deleteAll(Iterable<? extends Seat> entities) {
        repo.deleteAll(entities);
    }

    @Transactional
    public void deleteAll() {
        repo.deleteAll();
    }

    @Query("SELECT s FROM Seat s WHERE s.theater.id = :theater_id ORDER BY s.seat_row, s.seat_col")
    public List<Seat> findAllByTheaterId(long theater_id) {
        return repo.findAllByTheaterId(theater_id);
    }

    @Modifying
    @Transactional
    @Query("DELETE FROM Seat s WHERE s.theater.id = :theater_id")
    public void deleteAllByTheaterId(long theater_id) {
        repo.deleteAllByTheaterId(theater_id);
    }

    @Query("SELECT s.seat_col FROM Seat s WHERE s.theater.id = :theater_id GROUP BY s.seat_col")
    public List<String> getSeatColsByTheaterId(long theater_id) {
        return repo.getSeatColsByTheaterId(theater_id);
    }

    @Query("SELECT s.seat_row FROM Seat s WHERE s.theater.id = :theater_id GROUP BY s.seat_row")
    public List<String> getSeatRowsByTheaterId(long theater_id) {
        return repo.getSeatRowsByTheaterId(theater_id);
    }
}
