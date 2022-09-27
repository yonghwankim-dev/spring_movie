package kr.yh.movie.service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.repository.CinemaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log
public class CinemaService {
    private CinemaRepository cinemaRepository;
    public Predicate makePredicates(String type, String keyword) {
        return cinemaRepository.makePredicates(type, keyword);
    }

    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    public <S extends Cinema> S save(S entity) {
        return cinemaRepository.save(entity);
    }

    public <S extends Cinema> Iterable<S> saveAll(Iterable<S> entities) {
        return cinemaRepository.saveAll(entities);
    }

    public Optional<Cinema> findById(Long aLong) {
        return cinemaRepository.findById(aLong);
    }

    public boolean existsById(Long aLong) {
        return cinemaRepository.existsById(aLong);
    }

    public Iterable<Cinema> findAll() {
        return cinemaRepository.findAll();
    }

    public Iterable<Cinema> findAllById(Iterable<Long> longs) {
        return cinemaRepository.findAllById(longs);
    }

    public long count() {
        return cinemaRepository.count();
    }

    public void deleteById(Long aLong) {
        cinemaRepository.deleteById(aLong);
    }

    public void delete(Cinema entity) {
        cinemaRepository.delete(entity);
    }

    public void deleteAllById(Iterable<? extends Long> longs) {
        cinemaRepository.deleteAllById(longs);
    }

    public void deleteAll(Iterable<? extends Cinema> entities) {
        cinemaRepository.deleteAll(entities);
    }

    public void deleteAll() {
        cinemaRepository.deleteAll();
    }

    public Optional<Cinema> findOne(Predicate predicate) {
        return cinemaRepository.findOne(predicate);
    }

    public Iterable<Cinema> findAll(Predicate predicate) {
        return cinemaRepository.findAll(predicate);
    }

    public Iterable<Cinema> findAll(Predicate predicate, Sort sort) {
        return cinemaRepository.findAll(predicate, sort);
    }

    public Iterable<Cinema> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
        return cinemaRepository.findAll(predicate, orders);
    }

    public Iterable<Cinema> findAll(OrderSpecifier<?>... orders) {
        return cinemaRepository.findAll(orders);
    }

    public Page<Cinema> findAll(Predicate predicate, Pageable pageable) {
        return cinemaRepository.findAll(predicate, pageable);
    }

    public long count(Predicate predicate) {
        return cinemaRepository.count(predicate);
    }

    public boolean exists(Predicate predicate) {
        return cinemaRepository.exists(predicate);
    }

    public <S extends Cinema, R> R findBy(Predicate predicate, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return cinemaRepository.findBy(predicate, queryFunction);
    }
}
