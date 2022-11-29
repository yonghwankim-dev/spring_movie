package kr.yh.movie;

import kr.yh.movie.domain.Movie;
import kr.yh.movie.domain.Screen;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MovieApplicationTests {

    @Test
    public void test(){
        //given
        Movie movie1 = Movie.builder().id(1L).name("올빼미").filmRating(15).runtime(120).build();
        Movie movie2 = Movie.builder().id(1L).name("올빼미").filmRating(15).runtime(120).build();
        Map<Movie, List<Screen>> map = new HashMap<>();
        map.putIfAbsent(movie1, new ArrayList<>());
        //when
        List<Screen> actual = map.get(movie2);
        //then
        Assertions.assertThat(actual).isEqualTo(map.get(movie1));

    }
}
