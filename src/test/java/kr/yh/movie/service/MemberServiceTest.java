package kr.yh.movie.service;

import kr.yh.movie.domain.Reservation;
import kr.yh.movie.domain.member.Address;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.domain.member.MemberRole;
import kr.yh.movie.domain.member.MemberRoleName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional(readOnly = true)
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final static String NUMERIC = "0123456789";
    private final static Random random = new Random();

    @Test
    @Transactional
    public void insert() throws Exception{
        for (int i = 1; i <= 100; i++) {
            Member member = Member.builder()
                    .name("사용자"+i)
                    .birthday(getRandomBirthday())
                    .phone(randomCellPhone())
                    .address(new Address("06035", "서울 강남구 가로수길 5",""))
                    .email("user"+i+"@gmail.com")
                    .userId("user"+i)
                    .password(passwordEncoder.encode("pw"+i))
                    .gender(getGender(i))
                    .build();
            MemberRole role = MemberRole.builder()
                    .roleName(MemberRoleName.valueOf(getRole(i)))
                    .build();
            role.setMember(member);
            memberService.save(member);
        }
    }

    private static LocalDate getRandomBirthday(){
        GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(1950, 2022);

        gc.set(gc.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

        gc.set(gc.DAY_OF_YEAR, dayOfYear);

        return gc.toZonedDateTime().toLocalDate();
    }

    private static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    private static String randomCellPhone() throws Exception {
        return "010-" + randomNumberic(4) + "-" + randomNumberic(4);
    }

    private static String randomNumberic(int size) throws Exception {
        return randomChoice(NUMERIC, size);
    }

    private static String randomChoice(String strIdxs, int size) throws Exception {

        if (size < 1) {
            throw new Exception();
        }

        StringBuilder rtnStr = new StringBuilder();

        int i=0;
        while(i++ < size) {
            int random_idx = random.nextInt(strIdxs.length());
            rtnStr.append(strIdxs.substring(random_idx, random_idx+1));
        }

        return rtnStr.toString();
    }

    private static String getGender(int i){
        return i % 2 == 0 ? "male" : "female";
    }

    private static String getRole(int i){
        if(i <= 80){
            return "USER";
        }
        if(i <= 90){
            return "MANAGER";
        }
        if(i <= 100){
            return "ADMIN";
        }
        return "USER";
    }

    @Test
    @Transactional
    public void deleteAll(){
        memberService.deleteAll();
    }

    @Test
    @DisplayName("회원 삭제시 회원이 예매한 예매정보 삭제를 확인")
    @Transactional
    public void givenMember_whenDeleteMember_thenDeleteReservation(){
        //given
        Long deleteId = 1L;
        //when
        memberService.deleteById(deleteId);
        Reservation reservation = reservationService.findByMemberId(deleteId).orElse(null);
        //then
        assertThat(reservation).isNull();
    }
}
