package kr.yh.movie.repository;

import kr.yh.movie.controller.cinema.CinemaLocationDTO;
import kr.yh.movie.domain.Cinema;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    @Transactional
    public void testFindAll(){
        //given

        //when
        List<Cinema> cinemas = cinemaRepository.findAll();
        //then
        assertThat(cinemas.size()).isEqualTo(142);
    }

    private Cinema createCinemaEntity(long id, String name, String location) {
        return Cinema.builder()
                     .id(id)
                     .name(name)
                     .location(location)
                     .build();
    }

    @Test
    public void testFindAllLocationAndCountGroupByLocation(){
        //given
        String location = "서울";
        //when
        List<CinemaLocationDTO> cinemaLocationDTOS = cinemaRepository.findAllLocationAndCountGroupByLocation();
        CinemaLocationDTO actual = cinemaLocationDTOS.stream().filter(c -> c.getLocation().equals(location)).findFirst().get();
        //then
        assertThat(actual.getLocation()).isEqualTo(location);
        assertThat(actual.getCount()).isEqualTo(23);
    }

    @Test
    public void testFindAllLocationAndCountGroupByLocation_particularOrder(){
        //given
        String[] expected = {"서울", "경기/인천", "충청/대전", "전라/광주", "경북/대구", "경남/부산/울산", "강원", "제주"};
        //when
        List<CinemaLocationDTO> cinemaLocationDTOS = cinemaRepository.findAllLocationAndCountGroupByLocation();
        String[] actual = cinemaLocationDTOS.stream()
                                            .map(CinemaLocationDTO::getLocation)
                                            .collect(Collectors.joining(" "))
                                            .split(" ");
        //then
        assertThat(actual).isEqualTo(expected);
    }

}