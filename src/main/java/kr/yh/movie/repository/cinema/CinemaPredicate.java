package kr.yh.movie.repository.cinema;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.QCinema;

import java.util.List;

public class CinemaPredicate {
    private static final QCinema cinema = QCinema.cinema;

    public static Predicate search(String location){
        return search(location, (Long) null);
    }

    public static Predicate search(String location, Long cinemaId){
        List<Long> cinemaIdList = cinemaId != null ? List.of(cinemaId) : null;
        return search(location, cinemaIdList);
    }

    public static Predicate search(String location, List<Long> cinemaIdList){
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(greaterThanZeroCinemaId());
        builder.and(equalLocation(location));
        builder.andAnyOf(anyEqualCinemaId(cinemaIdList));
        return builder;
    }

    private static Predicate greaterThanZeroCinemaId(){
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(cinema.id.gt(0));
        return builder;
    }

    private static Predicate equalLocation(String location){
        BooleanBuilder builder = new BooleanBuilder();
        if(location != null){
            builder.and(cinema.location.eq(location));
        }
        return builder;
    }

    private static Predicate equalCinema(Long cinemaId){
        BooleanBuilder builder = new BooleanBuilder();
        if(cinemaId != null){
            builder.and(cinema.id.eq(cinemaId));
        }
        return builder;
    }

    private static Predicate anyEqualCinemaId(List<Long> cinemaIdList) {
        BooleanBuilder builder = new BooleanBuilder();
        if(cinemaIdList != null && !cinemaIdList.isEmpty()){
            cinemaIdList.stream().forEach(cinemaId -> builder.or(cinema.id.eq(cinemaId)));
        }
        return builder;
    }
}
