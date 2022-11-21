package kr.yh.movie.controller.converter;

import kr.yh.movie.domain.Movie;
import kr.yh.movie.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class LongToMovieConverter implements Converter<String, Movie> {
    private final MovieService movieService;

    @Override
    public Movie convert(String id) {
        return movieService.findById(Long.parseLong(id)).get();
    }
}
