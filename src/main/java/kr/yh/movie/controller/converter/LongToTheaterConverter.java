package kr.yh.movie.controller.converter;

import kr.yh.movie.domain.Theater;
import kr.yh.movie.service.TheaterService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LongToTheaterConverter implements Converter<String, Theater> {
    @Autowired
    private TheaterService theaterService;

    @Override
    public Theater convert(String id) {
        return theaterService.findById(Long.parseLong(id)).get();
    }
}
