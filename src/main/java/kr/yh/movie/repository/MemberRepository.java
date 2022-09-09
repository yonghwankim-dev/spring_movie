package kr.yh.movie.repository;

import kr.yh.movie.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public Member findOneByUserId(String userId){
        List<Member> members = em.createQuery("select m from Member m where m.userId = :userId", Member.class)
                .setParameter("userId", userId)
                .getResultList();
        return members.stream().findAny().orElse(null);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public void modifyOne(Member member){
        Member findMember = findOne(member.getId());
        findMember.changeInfo(member);
    }

    public void deleteOne(Long id){
        em.remove(findOne(id));
    }

    public boolean existByPhone(String phone){
        List<Member> members = em.createQuery("select m from Member m where m.phone = :phone", Member.class)
                .setParameter("phone",phone)
                .getResultList();
        return members.stream().findAny().isPresent();
    }

    public boolean existByEmail(String email){
        List<Member> members = em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email",email)
                .getResultList();
        return members.stream().findAny().isPresent();
    }

    public boolean existByUserId(String userId){
        List<Member> members = em.createQuery("select m from Member m where m.userId = :userId", Member.class)
                .setParameter("userId",userId)
                .getResultList();
        return members.stream().findAny().isPresent();
    }

    @Transactional
    public int updateMemberLastLogin(String userId, LocalDateTime lastLoginTime){
        int result = em.createQuery("update Member m set m.lastLoginTime = :lastLoginTime where m.userId = :userId", Member.class)
                        .setParameter("lastLoginTime", lastLoginTime)
                        .setParameter("userId", userId)
                        .executeUpdate();
        return result;
    }
}
