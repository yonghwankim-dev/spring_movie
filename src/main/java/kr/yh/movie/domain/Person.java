package kr.yh.movie.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Person {
    private int teenager;   // 청소년 인원수
    private int adult;      // 성인   인원수
    private int senior;     // 노약자 인원수
}
