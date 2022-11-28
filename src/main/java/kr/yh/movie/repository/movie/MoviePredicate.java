package kr.yh.movie.repository.movie;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.yh.movie.domain.QMovie;
import lombok.RequiredArgsConstructor;

public class MoviePredicate {
    public static Predicate search(Long movieId){
        BooleanBuilder builder = new BooleanBuilder();
        QMovie movie = QMovie.movie;

        builder.and(movie.id.gt(0));

        if(movieId != null){
            builder.and(movie.id.eq(movieId));
        }
        return builder;
    }
}
