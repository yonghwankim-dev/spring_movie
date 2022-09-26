package kr.yh.movie.domain.movie;

import kr.yh.movie.domain.Address;
import kr.yh.movie.domain.Movie;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.domain.member.MemberRole;
import kr.yh.movie.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@SpringBootTest
@Commit
public class MovieTest {
    @Autowired
    private MovieService movieService;

    @Test
    public void insert(){
        //given

        //when
        for (int i = 1; i <= 10; i++) {

            Movie movie = Movie.builder()
                               .name("영화"+i)
                               .filmRating(15)
                               .runtime(120)
                               .build();

            movieService.save(movie);
        }
        //then
    }
}
