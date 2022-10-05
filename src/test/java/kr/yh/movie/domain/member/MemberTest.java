package kr.yh.movie.domain.member;

import kr.yh.movie.domain.Address;
import kr.yh.movie.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Random;



@SpringBootTest
@Commit
public class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final static String NUMERIC = "0123456789";
    private final static Random random = new Random();

    @Test
    public void insert() throws Exception{
        //given
        //when
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
                                    .roles(Arrays.asList(new MemberRole(null, getRole(i))))
                                    .build();

            memberRepository.save(member);
        }
        //then
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
        return "010-" + randomNumric(4) + "-" + randomNumric(4);
    }
    private static String randomNumric(int size) throws Exception {
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





}