package kr.yh.movie.domain.member;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String zipcode;     // 우편번호
    private String street;      // 주소
    private String detail;      // 상세주소

    public void changeInfo(String zipcode, String street, String detail){
        this.zipcode = zipcode;
        this.street  = street;
        this.detail  = detail;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", zipcode, street, detail);
    }
}
