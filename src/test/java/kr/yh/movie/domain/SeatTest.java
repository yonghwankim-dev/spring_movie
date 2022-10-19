package kr.yh.movie.domain;

import kr.yh.movie.domain.Seat;
import kr.yh.movie.domain.Theater;
import kr.yh.movie.service.SeatService;
import kr.yh.movie.service.TheaterService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Commit
public class SeatTest {
    @Autowired
    private SeatService seatService;

    @Autowired
    private TheaterService theaterService;

    @Test
    public void insert(){
        //given
        Long theater_id = 1L;
        Theater theater = theaterService.findById(theater_id).get();
        String[] rows = {"A","B","C","D","E","F","G","H","I"};
        String[] cols = {"1","2","3","4","5","6","7","8","9"};
        //when
        for(int i = 0; i < rows.length; i++){
            for(int j = 0; j < cols.length; j++){
                Seat seat = Seat.builder()
                                .seat_row(rows[i])
                                .seat_col(cols[j])
                                .theater(theater)
                                .build();
                seatService.save(seat);
            }
        }
        //then
    }
}
