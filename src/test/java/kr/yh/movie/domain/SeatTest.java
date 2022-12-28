package kr.yh.movie.domain;

import kr.yh.movie.controller.seat.SeatForm;
import kr.yh.movie.controller.theater.TheaterForm;
import kr.yh.movie.domain.Seat;
import kr.yh.movie.domain.Theater;
import kr.yh.movie.service.SeatService;
import kr.yh.movie.service.TheaterService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


public class SeatTest {
    private Cinema cinema;
    private Theater theater;

    @BeforeEach
    public void setup(){
        cinema = Cinema.builder().id(1L).name("가산디지털").location("서울").build();
        theater = Theater.builder().id(1L).name("1관").build();
        theater.setCinema(cinema);
    }

    @Test
    public void testSetTheater(){
        //given
        Seat seat = Seat.builder().id(1L).seat_row("A").seat_col("1").build();
        //when
        seat.setTheater(theater);
        //then
        assertThat(seat.getTheater()).isEqualTo(theater);
        assertThat(theater.getSeats().contains(seat)).isTrue();
    }
    
    @Test
    public void testCreateSeat(){
        //given
        SeatForm form = SeatForm.builder().id(1L).seat_row("A").seat_col("1").theaterId(theater.getId()).build();
        //when
        Seat seat = Seat.createSeat(form, theater);
        //then
        assertThat(seat.getId()).isEqualTo(form.getId());
        assertThat(seat.getSeat_row()).isEqualTo(form.getSeat_row());
        assertThat(seat.getSeat_col()).isEqualTo(form.getSeat_col());
        assertThat(seat.getTheater()).isEqualTo(theater);
    }
    
    @Test
    public void testChangeInfo(){
        //given
        SeatForm form = SeatForm.builder().id(1L).seat_row("A").seat_col("1").theaterId(theater.getId()).build();
        Seat seat = Seat.builder().id(1L).seat_row("B").seat_col("1").build();
        seat.setTheater(theater);
        //when
        seat.changeInfo(form, theater);
        //then
        assertThat(seat.getId()).isEqualTo(1L);
        assertThat(seat.getSeat_row()).isEqualTo("A");
        assertThat(seat.getSeat_col()).isEqualTo("1");
        assertThat(seat.getTheater()).isEqualTo(theater);
    }
}
