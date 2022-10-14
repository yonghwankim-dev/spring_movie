package kr.yh.movie.config;

import kr.yh.movie.controller.converter.LongToMovieConverter;
import kr.yh.movie.controller.converter.LongToTheaterConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LongToMovieConverter());
        registry.addConverter(new LongToTheaterConverter());
    }
}
