package kr.yh.movie.domain;

import kr.yh.movie.service.ScreenService;
import kr.yh.movie.service.SeatService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
public class ScreenSeatTest {
    @Autowired
    private ScreenService screenService;
    @Autowired
    private SeatService seatService;
    @Test
    public void createScreenSeat(){
        //given
        Screen screen = screenService.findById(3L).get();
        List<Seat> seats = (List<Seat>) seatService.findAll(seatService.makePredicates(null, null, screen.getTheater().getId()));

        //when
        List<ScreenSeat> screenSeats = ScreenSeat.createScreenSeats(screen, seats);
        //then
        assertThat(screenSeats.size()).isEqualTo(81);
    }
}