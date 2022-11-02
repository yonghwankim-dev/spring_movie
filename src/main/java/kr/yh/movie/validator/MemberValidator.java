package kr.yh.movie.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
@AllArgsConstructor
public class MemberValidator {
    private final CheckUserIdValidator checkUserIdValidator;
    private final CheckPhoneValidator checkPhoneValidator;
    private final CheckEmailValidator checkEmailValidator;
    private final CheckPasswordEqualValidator checkPasswordEqualValidator;
    private final CheckBirthdayValidator checkBirthdayValidator;
}
