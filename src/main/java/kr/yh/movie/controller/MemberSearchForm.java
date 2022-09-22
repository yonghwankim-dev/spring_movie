package kr.yh.movie.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@ToString
public class MemberSearchForm {
    private String keyword;     // 검색분류 [전체, 이름, 핸드포번호, 아이디]
    private String content;     // 검색 내용
}
