package kr.yh.movie.repository;

import kr.yh.movie.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Member m WHERE m.phone = :phone")
    boolean existByPhone(@Param("phone") String phone);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Member m WHERE m.email = :email")
    boolean existByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Member m WHERE m.userId = :userId")
    boolean existByUserId(@Param("userId") String userId);

    @Transactional
    @Query("UPDATE Member m SET m.lastLoginTime = :lastLoginTime WHERE m.userId = :userId")
    int updateMemberLastLogin(@Param("userId") String userId,@Param("lastLoginTime") LocalDateTime lastLoginTime);
}
