package kr.yh.movie.controller.cinema;

import kr.yh.movie.controller.member.MemberDTO;
import kr.yh.movie.domain.Cinema;
import kr.yh.movie.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import javax.validation.constraints.NotEmpty;

import static kr.yh.movie.util.ModelMapperUtils.getModelMapper;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CinemaDTO {
    private Long id;
    @NotEmpty(message = "영화관 이름을 입력해주세요")
    private String name;
    private String location;

    public CinemaDTO(Cinema cinema){
        this.id         = cinema.getId();
        this.name       = cinema.getName();
        this.location   = cinema.getLocation();
    }

    public static CinemaDTO createCinemaDTO(){
        return new CinemaDTO();
    }

    public static CinemaDTO of(Cinema cinema){
        return getModelMapper().map(cinema, CinemaDTO.class);
    }
}
