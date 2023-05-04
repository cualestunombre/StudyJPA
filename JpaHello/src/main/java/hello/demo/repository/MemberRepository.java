package hello.demo.repository;

import hello.demo.domain.project.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberRepository {
    private final EntityManager em;

    public Member save(Member member){
        em.persist(member);
        return member;
    }
    public Optional<Member> find(Long id){
        Member member = em.find(Member.class,id);
        return Optional.ofNullable(member);

    }
    public List<Member> findAll(){
        //JPQL은 객체를 대상으로 하는 쿼리를 실행한다
        return em.createQuery("select m from Member m ", Member.class).getResultList();
    }

    public Member merge(Member member){
        return em.merge(member);
    }

}
