package kr.yh.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
enum PriceForPerson {
    TEENAGER(8000), ADULT(10000), SENIOR(5000);

    private int price;
}
