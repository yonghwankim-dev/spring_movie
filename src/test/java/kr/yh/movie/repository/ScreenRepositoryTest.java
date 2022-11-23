package kr.yh.movie.repository;

import kr.yh.movie.domain.Screen;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ScreenRepositoryTest {
    @Autowired
    ScreenRepository screenRepository;
    
    @Test
    @Transactional
    public void testFindAllByCinemaIdWithInnerJoinTheater(){
        //given
        Long cinemaId = 1L;
        //when
        List<Screen> actual = screenRepository.findAllByCinemaId(cinemaId);
        //then
        System.out.println(actual);
        Assertions.assertThat(actual).isNotNull();
    }
}