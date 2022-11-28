package kr.yh.movie.repository.screen;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.QScreen;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ScreenPredicate {
    private static final QScreen SCREEN = QScreen.screen;

    public static Predicate search(LocalDateTime startDate) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(equalStartDate(startDate));
        return builder;
    }

    private static Predicate equalStartDate(LocalDateTime startDate) {
        BooleanBuilder builder = new BooleanBuilder();
        if(startDate != null){
            builder.and(SCREEN.startDateTime.between(startDate, startDate.with(LocalTime.MAX)));
        }
        return builder;
    }
}
