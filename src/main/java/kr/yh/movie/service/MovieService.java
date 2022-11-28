package kr.yh.movie.service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.Movie;
import kr.yh.movie.repository.movie.MovieRepository;
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
public class MovieService {
    private final MovieRepository movieRepository;

    public Predicate makePredicates(String type, String keyword) {
        return movieRepository.makePredicates(type, keyword);
    }

    @Transactional
    public <S extends Movie> S save(S entity) {
        return movieRepository.save(entity);
    }

    public <S extends Movie> Iterable<S> saveAll(Iterable<S> entities) {
        return movieRepository.saveAll(entities);
    }

    public Optional<Movie> findById(Long aLong) {
        return movieRepository.findById(aLong);
    }

    public boolean existsById(Long aLong) {
        return movieRepository.existsById(aLong);
    }

    public Iterable<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Iterable<Movie> findAllById(Iterable<Long> longs) {
        return movieRepository.findAllById(longs);
    }

    public long count() {
        return movieRepository.count();
    }

    @Transactional
    public void deleteById(Long aLong) {
        movieRepository.deleteById(aLong);
    }

    @Transactional
    public void delete(Movie entity) {
        movieRepository.delete(entity);
    }

    @Transactional
    public void deleteAllById(Iterable<? extends Long> longs) {
        movieRepository.deleteAllById(longs);
    }

    @Transactional
    public void deleteAll(Iterable<? extends Movie> entities) {
        movieRepository.deleteAll(entities);
    }

    @Transactional
    public void deleteAll() {
        movieRepository.deleteAll();
    }

    public Optional<Movie> findOne(Predicate predicate) {
        return movieRepository.findOne(predicate);
    }

    public Iterable<Movie> findAll(Predicate predicate) {
        return movieRepository.findAll(predicate);
    }

    public Iterable<Movie> findAll(Predicate predicate, Sort sort) {
        return movieRepository.findAll(predicate, sort);
    }

    public Iterable<Movie> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
        return movieRepository.findAll(predicate, orders);
    }

    public Iterable<Movie> findAll(OrderSpecifier<?>... orders) {
        return movieRepository.findAll(orders);
    }

    public Page<Movie> findAll(Predicate predicate, Pageable pageable) {
        return movieRepository.findAll(predicate, pageable);
    }

    public long count(Predicate predicate) {
        return movieRepository.count(predicate);
    }

    public boolean exists(Predicate predicate) {
        return movieRepository.exists(predicate);
    }

    public <S extends Movie, R> R findBy(Predicate predicate, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return movieRepository.findBy(predicate, queryFunction);
    }
}
