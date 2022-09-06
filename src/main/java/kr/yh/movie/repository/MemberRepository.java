package kr.yh.movie.repository;

import kr.yh.movie.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
}
