package kr.yh.movie.service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.repository.CinemaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional
@RequiredArgsConstructor
@Log
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    public Predicate makePredicates(String type, String keyword) {
        return cinemaRepository.makePredicates(type, keyword);
    }

    public List<Cinema> findAll() {
        return cinemaRepository.findAll();
    }

    public List<Cinema> findAll(Sort sort) {
        return cinemaRepository.findAll(sort);
    }

    public List<Cinema> findAllById(Iterable<Long> longs) {
        return cinemaRepository.findAllById(longs);
    }

    public <S extends Cinema> List<S> saveAll(Iterable<S> entities) {
        return cinemaRepository.saveAll(entities);
    }

    public void flush() {
        cinemaRepository.flush();
    }

    public <S extends Cinema> S saveAndFlush(S entity) {
        return cinemaRepository.saveAndFlush(entity);
    }

    public <S extends Cinema> List<S> saveAllAndFlush(Iterable<S> entities) {
        return cinemaRepository.saveAllAndFlush(entities);
    }

    @Deprecated
    public void deleteInBatch(Iterable<Cinema> entities) {
        cinemaRepository.deleteInBatch(entities);
    }

    public void deleteAllInBatch(Iterable<Cinema> entities) {
        cinemaRepository.deleteAllInBatch(entities);
    }

    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        cinemaRepository.deleteAllByIdInBatch(longs);
    }

    public void deleteAllInBatch() {
        cinemaRepository.deleteAllInBatch();
    }

    @Deprecated
    public Cinema getOne(Long aLong) {
        return cinemaRepository.getOne(aLong);
    }

    @Deprecated
    public Cinema getById(Long aLong) {
        return cinemaRepository.getById(aLong);
    }

    public Cinema getReferenceById(Long aLong) {
        return cinemaRepository.getReferenceById(aLong);
    }

    public <S extends Cinema> List<S> findAll(Example<S> example) {
        return cinemaRepository.findAll(example);
    }

    public <S extends Cinema> List<S> findAll(Example<S> example, Sort sort) {
        return cinemaRepository.findAll(example, sort);
    }

    public Page<Cinema> findAll(Pageable pageable) {
        return cinemaRepository.findAll(pageable);
    }

    public <S extends Cinema> S save(S entity) {
        return cinemaRepository.save(entity);
    }

    public Optional<Cinema> findById(Long aLong) {
        return cinemaRepository.findById(aLong);
    }

    public boolean existsById(Long aLong) {
        return cinemaRepository.existsById(aLong);
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

    public <S extends Cinema> Optional<S> findOne(Example<S> example) {
        return cinemaRepository.findOne(example);
    }

    public <S extends Cinema> Page<S> findAll(Example<S> example, Pageable pageable) {
        return cinemaRepository.findAll(example, pageable);
    }

    public <S extends Cinema> long count(Example<S> example) {
        return cinemaRepository.count(example);
    }

    public <S extends Cinema> boolean exists(Example<S> example) {
        return cinemaRepository.exists(example);
    }

    public <S extends Cinema, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return cinemaRepository.findBy(example, queryFunction);
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
