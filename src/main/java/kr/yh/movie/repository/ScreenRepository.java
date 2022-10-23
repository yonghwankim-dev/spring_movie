package kr.yh.movie.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ScreenRepository extends CrudRepository<Screen, Long>, QuerydslPredicateExecutor<Screen> {
    default Predicate makePredicates(String type, String keyword){
        BooleanBuilder builder = new BooleanBuilder();
        QScreen screen = QScreen.screen;

        // type if ~ else

        // id > 0
        builder.and(screen.id.gt(0));

        if(type == null){
            return builder;
        }

        if(type.equals("movie")){
            builder.and(screen.movie.name.like("%"+keyword+"%"));
        }else if(type.equals("theater")){
            builder.and(screen.theater.name.like("%"+keyword+"%"));
        }else if(type.equals("round")){
            builder.and(screen.round.eq(Integer.parseInt(keyword)));
        }

        return builder;
    }

    @Query("SELECT s FROM Screen s WHERE s.id = (SELECT MIN(id) FROM Screen)")
    Screen findFirstByScreenId();

}
