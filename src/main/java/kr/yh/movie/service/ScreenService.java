package kr.yh.movie.service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.Screen;
import kr.yh.movie.repository.ScreenRepository;
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
public class ScreenService {
    private final ScreenRepository screenRepository;

    public Predicate makePredicates(String type, String keyword) {
        return screenRepository.makePredicates(type, keyword);
    }

    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    @Transactional
    public <S extends Screen> S save(S entity) {
        return screenRepository.save(entity);
    }

    @Transactional
    public <S extends Screen> Iterable<S> saveAll(Iterable<S> entities) {
        return screenRepository.saveAll(entities);
    }

    public Optional<Screen> findById(Long aLong) {
        return screenRepository.findById(aLong);
    }

    public boolean existsById(Long aLong) {
        return screenRepository.existsById(aLong);
    }

    public Iterable<Screen> findAll() {
        return screenRepository.findAll();
    }

    public Iterable<Screen> findAllById(Iterable<Long> longs) {
        return screenRepository.findAllById(longs);
    }

    public long count() {
        return screenRepository.count();
    }

    @Transactional
    public void deleteById(Long aLong) {
        screenRepository.deleteById(aLong);
    }

    @Transactional
    public void delete(Screen entity) {
        screenRepository.delete(entity);
    }

    @Transactional
    public void deleteAllById(Iterable<? extends Long> longs) {
        screenRepository.deleteAllById(longs);
    }

    @Transactional
    public void deleteAll(Iterable<? extends Screen> entities) {
        screenRepository.deleteAll(entities);
    }

    @Transactional
    public void deleteAll() {
        screenRepository.deleteAll();
    }

    public Optional<Screen> findOne(Predicate predicate) {
        return screenRepository.findOne(predicate);
    }

    public Iterable<Screen> findAll(Predicate predicate) {
        return screenRepository.findAll(predicate);
    }

    public Iterable<Screen> findAll(Predicate predicate, Sort sort) {
        return screenRepository.findAll(predicate, sort);
    }

    public Iterable<Screen> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
        return screenRepository.findAll(predicate, orders);
    }

    public Iterable<Screen> findAll(OrderSpecifier<?>... orders) {
        return screenRepository.findAll(orders);
    }

    public Page<Screen> findAll(Predicate predicate, Pageable pageable) {
        return screenRepository.findAll(predicate, pageable);
    }

    public long count(Predicate predicate) {
        return screenRepository.count(predicate);
    }

    public boolean exists(Predicate predicate) {
        return screenRepository.exists(predicate);
    }

    public <S extends Screen, R> R findBy(Predicate predicate, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return screenRepository.findBy(predicate, queryFunction);
    }
}
