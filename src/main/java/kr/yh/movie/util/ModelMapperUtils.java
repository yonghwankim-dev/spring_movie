package kr.yh.movie.util;

import kr.yh.movie.controller.member.MemberDTO;
import kr.yh.movie.domain.member.Member;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static org.modelmapper.config.Configuration.AccessLevel.*;

@Component
public class ModelMapperUtils {

    @Bean
    public static ModelMapper getModelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                    .setFieldAccessLevel(PRIVATE)
                    .setFieldMatchingEnabled(true);
        return modelMapper;
    }
}
