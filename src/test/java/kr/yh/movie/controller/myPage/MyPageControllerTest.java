package kr.yh.movie.controller.myPage;

import kr.yh.movie.controller.converter.LongToMovieConverter;
import kr.yh.movie.controller.converter.LongToTheaterConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(value = MyInfoController.class,
            excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                    classes = {LongToMovieConverter.class, LongToTheaterConverter.class}))
public class MyPageControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testMyPage() throws Exception {
        mockMvc.perform(get("/myPage")
                .contentType(TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("/myPage/myPage"));
    }

}