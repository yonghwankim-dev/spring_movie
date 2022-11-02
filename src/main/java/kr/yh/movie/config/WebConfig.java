package kr.yh.movie.config;

import kr.yh.movie.controller.converter.LongToMovieConverter;
import kr.yh.movie.controller.converter.LongToTheaterConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LongToMovieConverter());
        registry.addConverter(new LongToTheaterConverter());
    }
}
