package kr.yh.movie.controller;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SeatModifyForm {
    private List<String> seat;
}
