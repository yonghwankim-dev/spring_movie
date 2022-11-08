package kr.yh.movie.config;

import kr.yh.movie.controller.converter.LongToMovieConverter;
import kr.yh.movie.controller.converter.LongToTheaterConverter;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@WebAppConfiguration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final LongToMovieConverter longToMovieConverter;
    private final LongToTheaterConverter longToTheaterConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(longToMovieConverter);
        registry.addConverter(longToTheaterConverter);
    }
}
