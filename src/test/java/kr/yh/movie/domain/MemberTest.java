package kr.yh.movie.domain;

import kr.yh.movie.domain.member.Address;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.domain.member.MemberRole;
import kr.yh.movie.domain.member.MemberRoleName;
import kr.yh.movie.repository.MemberRepository;
import kr.yh.movie.service.MemberService;
import kr.yh.movie.service.ReservationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional(readOnly = true)
public class MemberTest {

}