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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Log
public class MemberService{
    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional
    public void signUp(Member member){
        memberRepository.save(member);
    }

    // 회원가입시 유효성 체크
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
        for(FieldError error : errors.getFieldErrors()){
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    @Query("SELECT m FROM Member m WHERE m.userId = :userId")
    public Optional<Member> findByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Member m WHERE m.phone = :phone")
    public boolean existByPhone(String phone) {
        return memberRepository.existByPhone(phone);
    }

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Member m WHERE m.email = :email")
    public boolean existByEmail(String email) {
        return memberRepository.existByEmail(email);
    }

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Member m WHERE m.userId = :userId")
    public boolean existByUserId(String userId) {
        return memberRepository.existByUserId(userId);
    }

    @Query("UPDATE Member m SET m.lastLoginTime = :lastLoginTime WHERE m.userId = :userId")
    @Transactional
    public int updateMemberLastLogin(String userId, LocalDateTime lastLoginTime) {
        return memberRepository.updateMemberLastLogin(userId, lastLoginTime);
    }

    public Predicate makePredicates(String type, String keyword) {
        return memberRepository.makePredicates(type, keyword);
    }

    @Transactional
    public <S extends Member> S save(S entity) {
        return memberRepository.save(entity);
    }

    public <S extends Member> Iterable<S> saveAll(Iterable<S> entities) {
        return memberRepository.saveAll(entities);
    }

    public Optional<Member> findById(Long aLong) {
        return memberRepository.findById(aLong);
    }

    public boolean existsById(Long aLong) {
        return memberRepository.existsById(aLong);
    }

    public Iterable<Member> findAll() {
        return memberRepository.findAll();
    }

    public Iterable<Member> findAllById(Iterable<Long> longs) {
        return memberRepository.findAllById(longs);
    }

    public long count() {
        return memberRepository.count();
    }

    @Transactional
    public void deleteById(Long aLong) {
        memberRepository.deleteById(aLong);
    }

    public void delete(Member entity) {
        memberRepository.delete(entity);
    }

    @Transactional
    public void deleteAllById(Iterable<? extends Long> longs) {
        memberRepository.deleteAllById(longs);
    }

    public void deleteAll(Iterable<? extends Member> entities) {
        memberRepository.deleteAll(entities);
    }

    public void deleteAll() {
        memberRepository.deleteAll();
    }

    public Optional<Member> findOne(Predicate predicate) {
        return memberRepository.findOne(predicate);
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

    public <S extends Member, R> R findBy(Predicate predicate, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return memberRepository.findBy(predicate, queryFunction);
    }
}
