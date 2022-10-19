package kr.yh.movie.service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.Theater;
import kr.yh.movie.repository.CinemaRepository;
import kr.yh.movie.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
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
public class TheaterService {
    private final TheaterRepository repo;

    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    @Transactional
    public <S extends Theater> S save(S entity) {
        return repo.save(entity);
    }

    @Transactional
    public <S extends Theater> Iterable<S> saveAll(Iterable<S> entities) {
        return repo.saveAll(entities);
    }

    public Optional<Theater> findById(Long aLong) {
        return repo.findById(aLong);
    }

    public boolean existsById(Long aLong) {
        return repo.existsById(aLong);
    }

    public Iterable<Theater> findAll() {
        return repo.findAll();
    }

    public Iterable<Theater> findAllById(Iterable<Long> longs) {
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
    public void delete(Theater entity) {
        repo.delete(entity);
    }

    @Transactional
    public void deleteAllById(Iterable<? extends Long> longs) {
        repo.deleteAllById(longs);
    }

    @Transactional
    public void deleteAll(Iterable<? extends Theater> entities) {
        repo.deleteAll(entities);
    }

    @Transactional
    public void deleteAll() {
        repo.deleteAll();
    }

    public Optional<Theater> findOne(Predicate predicate) {
        return repo.findOne(predicate);
    }

    public Iterable<Theater> findAll(Predicate predicate) {
        return repo.findAll(predicate);
    }

    public Iterable<Theater> findAll(Predicate predicate, Sort sort) {
        return repo.findAll(predicate, sort);
    }

    public Iterable<Theater> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
        return repo.findAll(predicate, orders);
    }

    public Iterable<Theater> findAll(OrderSpecifier<?>... orders) {
        return repo.findAll(orders);
    }

    public Page<Theater> findAll(Predicate predicate, Pageable pageable) {
        return repo.findAll(predicate, pageable);
    }

    public long count(Predicate predicate) {
        return repo.count(predicate);
    }

    public boolean exists(Predicate predicate) {
        return repo.exists(predicate);
    }

    public <S extends Theater, R> R findBy(Predicate predicate, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return repo.findBy(predicate, queryFunction);
    }

    @Query("SELECT t FROM Theater t WHERE t.cinema.id = :cinemaId")
    public List<Theater> findAllByCinemaId(Long cinemaId) {
        return repo.findAllByCinemaId(cinemaId);
    }

    public Predicate makePredicates(String type, String keyword, Long cinemaId) {
        return repo.makePredicates(type, keyword, cinemaId);
    }
}
