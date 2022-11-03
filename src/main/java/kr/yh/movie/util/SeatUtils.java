package kr.yh.movie.util;

import kr.yh.movie.domain.Seat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class SeatUtils {
    public static List<List<Seat>> to2DList(List<Seat> seats, int rows){
        List<List<Seat>> result = new ArrayList<>();
        IntStream.range(0,rows).forEach(i->result.add(new ArrayList<>()));

        for(Seat seat : seats){
            result.get(getRowIndex(seat.getSeat_row())).add(seat);
        }
        return result;
    }

    private static int getRowIndex(String row){
        return (int) row.charAt(0) - 65;
    }
}
