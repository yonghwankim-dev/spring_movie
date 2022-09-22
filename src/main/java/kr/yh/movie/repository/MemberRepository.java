package kr.yh.movie.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.domain.member.QMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long>, QuerydslPredicateExecutor<Member> {

    @Query("SELECT m FROM Member m WHERE m.userId = :userId")
    Optional<Member> findByUserId(@Param("userId") String userId);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Member m WHERE m.phone = :phone")
    boolean existByPhone(@Param("phone") String phone);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Member m WHERE m.email = :email")
    boolean existByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Member m WHERE m.userId = :userId")
    boolean existByUserId(@Param("userId") String userId);

    @Transactional
    @Query("UPDATE Member m SET m.lastLoginTime = :lastLoginTime WHERE m.userId = :userId")
    int updateMemberLastLogin(@Param("userId") String userId,@Param("lastLoginTime") LocalDateTime lastLoginTime);

    default Predicate makePredicates(String type, String keyword){
        BooleanBuilder builder = new BooleanBuilder();
        QMember member = QMember.member;

        // type if ~ else

        // id > 0
        builder.and(member.id.gt(0));

        if(type == null){
            return builder;
        }

        if(type.equals("name")){
            builder.and(member.name.like("%"+keyword+"%"));
        }else if(type.equals("phone")){
            builder.and(member.phone.like("%"+keyword+"%"));
        }else if(type.equals("userId")){
            builder.and(member.userId.like("%"+keyword+"%"));
        }

        return builder;
    }
}
