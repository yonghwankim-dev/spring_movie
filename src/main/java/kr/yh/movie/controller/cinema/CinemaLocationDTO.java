package kr.yh.movie.controller.cinema;

import kr.yh.movie.domain.Cinema;
import lombok.*;

import static kr.yh.movie.util.ModelMapperUtils.getModelMapper;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CinemaLocationDTO {
    private String location;
    private Long count;
}
