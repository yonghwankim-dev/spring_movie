package kr.yh.movie.util;

public class MemberRegExpUtils {
    public static final String NAME_FORMAT = "^[가-힣]{2,4}|[a-zA-Z]{2,10}\\s[a-zA-Z]{2,10}$";
    public static final String PHONE_FORMAT = "^\\d{3}-\\d{4}-\\d{4}$";
    public static final String USERID_FORMAT = "^[A-Za-z]+[A-Za-z0-9]{4,20}$";
    public static final String PASSWORD_FORMAT = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}";
}
