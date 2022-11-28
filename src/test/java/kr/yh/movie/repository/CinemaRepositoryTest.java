package kr.yh.movie.repository;

import kr.yh.movie.config.QuerydslConfig;
import kr.yh.movie.controller.cinema.CinemaLocationDTO;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.repository.cinema.CinemaRepository;
import kr.yh.movie.repository.cinema.CinemaRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfig.class)
public class CinemaRepositoryTest {

    @Autowired
    CinemaRepository cinemaRepository;

    @Autowired
    CinemaRepositoryImpl cinemaRepositoryImpl;

    @Test
    @DisplayName("영화관 저장")
    public void testSave() {
        //given
        Cinema cinema = Cinema.builder()
                .id(1L)
                .name("가산디지털")
                .location("서울")
                .build();

        //when
        Cinema save = cinemaRepository.save(cinema);

        //then
        assertThat(save).isEqualTo(cinema);
    }

    @Test
    @DisplayName("전체 영화관 개수 = 142개")
    public void testFindAll() {
        //given

        //when
        List<Cinema> cinemas = cinemaRepository.findAll();
        //then
        assertThat(cinemas.size()).isEqualTo(142);
    }


    @Test
    @DisplayName("서울 지역 영화관 = 23개")
    public void testFindAllLocationAndCountGroupByLocation() {
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
    @DisplayName("지역 리스트 = 특정한 지역 순서")
    public void testFindAllLocationAndCountGroupByLocation_particularOrder() {
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


    @Test
    @DisplayName("지역 + 상영일")
    public void testFindAllByLocationAndStartDate() {
        //given
        String location = "서울";
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        //when
        List<Cinema> actual = cinemaRepositoryImpl.findAll(location, startDate, null, null);
        //then
        assertThat(actual.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("지역 + 상영일 + 영화관")
    public void testFindAllByLocationAndStartDateAndCinema(){
        //given
        String location = "서울";
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        Long cinemaId = 1L;
        //when
        List<Cinema> actual = cinemaRepositoryImpl.findAll(location, startDate,cinemaId);
        //then
        assertThat(actual.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("지역 + 상영일 + 영화")
    public void testFindAllByLocationAndStartDateAndMovie() {
        //given
        String location = "서울";
        LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        Long movieId = 1L;
        //when
        List<Cinema> actual = cinemaRepositoryImpl.findAll(location, startDate, null, movieId);
        //then
        assertThat(actual.size()).isEqualTo(2);
    }

    @Test
    public void test(){
        //given

        //when
        String s = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT).format(DateTimeFormatter.ISO_LOCAL_DATE).toString();
        //then
        System.out.println(s);
    }

}