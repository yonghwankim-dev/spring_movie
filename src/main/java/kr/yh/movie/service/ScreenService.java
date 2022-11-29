package kr.yh.movie.service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.Screen;
import kr.yh.movie.domain.ScreenSeat;
import kr.yh.movie.domain.Seat;
import kr.yh.movie.repository.screen.ScreenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;

import static kr.yh.movie.domain.ScreenSeatStatus.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log
public class ScreenService {
    private final ScreenRepository screenRepository;
    private final ScreenSeatService screenSeatService;
    private final SeatService seatService;

    public Predicate makePredicates(String type, String keyword) {
        return screenRepository.makePredicates(type, keyword);
    }

    @Transactional
    public <S extends Screen> S save(S entity) {
        Screen savedScreen = screenRepository.save(entity);
        log.info("savedScreen screenSeats : " + savedScreen.getScreenSeats());
        log.info("entity : " + entity);
        screenSeatService.saveAll(createScreenSeats(savedScreen));
        return (S) savedScreen;
    }

    private List<ScreenSeat> createScreenSeats(Screen screen){
        List<ScreenSeat> screenSeats = new ArrayList<>();
        List<Seat> seats = seatService.findAllByTheaterId(screen.getTheater().getId());

        for(Seat seat : seats){
            ScreenSeat screenSeat = ScreenSeat.builder().status(EMPTY).build();
            screenSeat.setScreen(screen);
            screenSeat.setSeat(seat);
            screenSeats.add(screenSeat);
        }
        return screenSeats;
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
