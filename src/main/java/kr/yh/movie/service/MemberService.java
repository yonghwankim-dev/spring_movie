package kr.yh.movie.service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import kr.yh.movie.domain.member.Member;
import kr.yh.movie.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log
public class MemberService{
    private final MemberRepository memberRepository;

    public Predicate makePredicates(String type, String keyword) {
        return memberRepository.makePredicates(type, keyword);
    }

    @Transactional
    public <S extends Member> S save(S entity) {
        return memberRepository.save(entity);
    }

    public Optional<Member> findById(Long aLong) {
        return memberRepository.findById(aLong);
    }

    public Iterable<Member> findAll() {
        return memberRepository.findAll();
    }

    public long count() {
        return memberRepository.count();
    }

    @Transactional
    public void deleteById(Long aLong) {
        memberRepository.deleteById(aLong);
    }

    @Transactional
    public void delete(Member entity) {
        memberRepository.delete(entity);
    }

    @Transactional
    public void deleteAllById(Iterable<? extends Long> longs) {
        memberRepository.deleteAllById(longs);
    }

    @Transactional
    public void deleteAll() {
        memberRepository.deleteAll();
    }

    public Iterable<Member> findAll(Predicate predicate) {
        return memberRepository.findAll(predicate);
    }

    public Iterable<Member> findAll(Predicate predicate, Sort sort) {
        return memberRepository.findAll(predicate, sort);
    }

    public Iterable<Member> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
        return memberRepository.findAll(predicate, orders);
    }

    public Iterable<Member> findAll(OrderSpecifier<?>... orders) {
        return memberRepository.findAll(orders);
    }

    public Page<Member> findAll(Predicate predicate, Pageable pageable) {
        return memberRepository.findAll(predicate, pageable);
    }

    public long count(Predicate predicate) {
        return memberRepository.count(predicate);
    }

    public boolean exists(Predicate predicate) {
        return memberRepository.exists(predicate);
    }

}
