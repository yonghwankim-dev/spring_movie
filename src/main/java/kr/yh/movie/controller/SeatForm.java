package kr.yh.movie.controller;

import kr.yh.movie.domain.Theater;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SeatForm {
    private Long id;
    @NotEmpty(message = "좌석행은 A~Z 사이의 갑싱어야 합니다.")
    private String seat_row;
    @NotEmpty(message = "좌석열은 자연수여야 합니다.")
    private String seat_col;
    private Theater theater;
}
