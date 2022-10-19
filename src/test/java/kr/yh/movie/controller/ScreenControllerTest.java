package kr.yh.movie.controller;

import kr.yh.movie.domain.Screen;
import kr.yh.movie.vo.PageMarker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional(readOnly = true)
public class ScreenControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testList() throws Exception {
        //given
        String url = "/screens/list";
        //when
        Object result = this.mockMvc.perform(get(url)
                                    .contentType(MediaType.TEXT_HTML))
                                    .andExpect(status().isOk())
                                    .andReturn().getModelAndView().getModel().get("result");
        //then
        assertThat(result).isNotNull();
    }
}