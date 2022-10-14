package kr.yh.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
public class Person {
    private int teenager;
    private int adult;
    private int senior;
}
