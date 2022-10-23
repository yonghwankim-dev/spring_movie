package kr.yh.movie.service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.Reservation;
import kr.yh.movie.repository.CinemaRepository;
import kr.yh.movie.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public Predicate makePredicates(String type, String keyword, Long cinemaId) {
        return reservationRepository.makePredicates(type, keyword, cinemaId);
    }

    public <S extends Reservation> S save(S entity) {
        return reservationRepository.save(entity);
    }

    public <S extends Reservation> Iterable<S> saveAll(Iterable<S> entities) {
        return reservationRepository.saveAll(entities);
    }

    public Optional<Reservation> findById(Long aLong) {
        return reservationRepository.findById(aLong);
    }

    public boolean existsById(Long aLong) {
        return reservationRepository.existsById(aLong);
    }

    public Iterable<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Iterable<Reservation> findAllById(Iterable<Long> longs) {
        return reservationRepository.findAllById(longs);
    }

    public long count() {
        return reservationRepository.count();
    }

    public void deleteById(Long aLong) {
        reservationRepository.deleteById(aLong);
    }

    public void delete(Reservation entity) {
        reservationRepository.delete(entity);
    }

    public void deleteAllById(Iterable<? extends Long> longs) {
        reservationRepository.deleteAllById(longs);
    }

    public void deleteAll(Iterable<? extends Reservation> entities) {
        reservationRepository.deleteAll(entities);
    }

    public void deleteAll() {
        reservationRepository.deleteAll();
    }

    public Optional<Reservation> findOne(Predicate predicate) {
        return reservationRepository.findOne(predicate);
    }

    public Iterable<Reservation> findAll(Predicate predicate) {
        return reservationRepository.findAll(predicate);
    }

    public Iterable<Reservation> findAll(Predicate predicate, Sort sort) {
        return reservationRepository.findAll(predicate, sort);
    }

    public Iterable<Reservation> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
        return reservationRepository.findAll(predicate, orders);
    }

    public Iterable<Reservation> findAll(OrderSpecifier<?>... orders) {
        return reservationRepository.findAll(orders);
    }

    public Page<Reservation> findAll(Predicate predicate, Pageable pageable) {
        return reservationRepository.findAll(predicate, pageable);
    }

    public long count(Predicate predicate) {
        return reservationRepository.count(predicate);
    }

    public boolean exists(Predicate predicate) {
        return reservationRepository.exists(predicate);
    }

    public <S extends Reservation, R> R findBy(Predicate predicate, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return reservationRepository.findBy(predicate, queryFunction);
    }
}
