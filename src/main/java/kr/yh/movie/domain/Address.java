package kr.yh.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String zipcode;     // 우편번호
    private String street;      // 주소
    private String detail;      // 상세주소

    @Override
    public String toString() {
        return String.format("%s %s %s", zipcode, street, detail);
    }
}
