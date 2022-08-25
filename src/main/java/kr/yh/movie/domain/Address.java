package kr.yh.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String city;        // 도시
    private String street;      // 거리
    private String zipcode;     // 우편번호
}
