package kr.yh.movie.repository;

import kr.yh.movie.domain.Cinema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CinemaRepositoryTest {

    @Autowired
    CinemaRepository cinemaRepository;

    @Test
    public void testSave(){
        //given
        Cinema cinema = createCinemaEntity(1L, "가산디지털", "서울");

        //when
        Cinema save = cinemaRepository.save(cinema);

        //then
        assertThat(save.getName()).isEqualTo(cinema.getName());
        assertThat(save.getLocation()).isEqualTo(cinema.getLocation());
    }

    @Test
    public void testFindAll(){
        //given
        Cinema cinema = createCinemaEntity(1L, "가산디지털", "서울");
        cinemaRepository.save(cinema);
        //when
        List<Cinema> cinemas = cinemaRepository.findAll();

        //then
        assertThat(cinemas).isEqualTo(List.of(cinema));
    }

    private Cinema createCinemaEntity(long id, String name, String location) {
        return Cinema.builder()
                     .id(id)
                     .name(name)
                     .location(location)
                     .build();
    }

}