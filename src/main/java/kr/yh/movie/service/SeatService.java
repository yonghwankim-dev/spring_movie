package kr.yh.movie.service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.Seat;
import kr.yh.movie.domain.Theater;
import kr.yh.movie.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log
public class SeatService {
    private final TheaterService theaterService;
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

    public Optional<Seat> findOne(Predicate predicate) {
        return repo.findOne(predicate);
    }

    public Iterable<Seat> findAll(Predicate predicate) {
        return repo.findAll(predicate);
    }

    public Iterable<Seat> findAll(Predicate predicate, Sort sort) {
        return repo.findAll(predicate, sort);
    }

    public Iterable<Seat> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
        return repo.findAll(predicate, orders);
    }

    public Iterable<Seat> findAll(OrderSpecifier<?>... orders) {
        return repo.findAll(orders);
    }

    public Page<Seat> findAll(Predicate predicate, Pageable pageable) {
        return repo.findAll(predicate, pageable);
    }

    public long count(Predicate predicate) {
        return repo.count(predicate);
    }

    public boolean exists(Predicate predicate) {
        return repo.exists(predicate);
    }

    public <S extends Seat, R> R findBy(Predicate predicate, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return repo.findBy(predicate, queryFunction);
    }

    public Predicate makePredicates(String type, String keyword, Long theaterId) {
        return repo.makePredicates(type, keyword, theaterId);
    }

    @Query("SELECT s FROM Seat s WHERE s.theater.id = :theaterId AND s.id > 0")
    public List<Seat> findAllByTheaterId(Long theaterId) {
        return repo.findAllByTheaterId(theaterId);
    }
}
