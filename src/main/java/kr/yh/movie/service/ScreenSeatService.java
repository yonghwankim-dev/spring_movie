package kr.yh.movie.service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.ScreenSeat;
import kr.yh.movie.repository.ScreenSeatRepository;
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
public class ScreenSeatService {
    private final ScreenSeatRepository screenSeatRepository;

    public Predicate makePredicates(String type, String keyword) {
        return screenSeatRepository.makePredicates(type, keyword);
    }

    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    public <S extends ScreenSeat> S save(S entity) {
        return screenSeatRepository.save(entity);
    }

    public <S extends ScreenSeat> Iterable<S> saveAll(Iterable<S> entities) {
        return screenSeatRepository.saveAll(entities);
    }

    public Optional<ScreenSeat> findById(Long aLong) {
        return screenSeatRepository.findById(aLong);
    }

    public boolean existsById(Long aLong) {
        return screenSeatRepository.existsById(aLong);
    }

    public Iterable<ScreenSeat> findAll() {
        return screenSeatRepository.findAll();
    }

    public Iterable<ScreenSeat> findAllById(Iterable<Long> longs) {
        return screenSeatRepository.findAllById(longs);
    }

    public long count() {
        return screenSeatRepository.count();
    }

    public void deleteById(Long aLong) {
        screenSeatRepository.deleteById(aLong);
    }

    public void delete(ScreenSeat entity) {
        screenSeatRepository.delete(entity);
    }

    public void deleteAllById(Iterable<? extends Long> longs) {
        screenSeatRepository.deleteAllById(longs);
    }

    public void deleteAll(Iterable<? extends ScreenSeat> entities) {
        screenSeatRepository.deleteAll(entities);
    }

    public void deleteAll() {
        screenSeatRepository.deleteAll();
    }

    public Optional<ScreenSeat> findOne(Predicate predicate) {
        return screenSeatRepository.findOne(predicate);
    }

    public Iterable<ScreenSeat> findAll(Predicate predicate) {
        return screenSeatRepository.findAll(predicate);
    }

    public Iterable<ScreenSeat> findAll(Predicate predicate, Sort sort) {
        return screenSeatRepository.findAll(predicate, sort);
    }

    public Iterable<ScreenSeat> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
        return screenSeatRepository.findAll(predicate, orders);
    }

    public Iterable<ScreenSeat> findAll(OrderSpecifier<?>... orders) {
        return screenSeatRepository.findAll(orders);
    }

    public Page<ScreenSeat> findAll(Predicate predicate, Pageable pageable) {
        return screenSeatRepository.findAll(predicate, pageable);
    }

    public long count(Predicate predicate) {
        return screenSeatRepository.count(predicate);
    }

    public boolean exists(Predicate predicate) {
        return screenSeatRepository.exists(predicate);
    }

    public <S extends ScreenSeat, R> R findBy(Predicate predicate, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return screenSeatRepository.findBy(predicate, queryFunction);
    }
}
