package kr.yh.movie.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import static org.modelmapper.config.Configuration.AccessLevel.*;

public class ModelMapperUtils {
    private static ModelMapper modelMapper = new ModelMapper();

    private ModelMapperUtils(){

    }

    public static ModelMapper getModelMapper(){
        modelMapper.getConfiguration()
                    .setFieldAccessLevel(PRIVATE)
                    .setFieldMatchingEnabled(true);
        return modelMapper;
    }
}
