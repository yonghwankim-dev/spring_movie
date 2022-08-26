package kr.yh.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cinema_id")
    private Long id;            // 영화관번호
    private String name;        // 이름
    private String location;    // 지역

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
    private final List<Theater> theaters = new ArrayList<Theater>();
}
