package hello.jpa.repository;

import hello.jpa.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member){ //멤버를 저장
        em.persist(member);

    }

    public Member findOne(Long id){
        return em.find(Member.class,id);
    } //id로 멤버를 찾음

    public List<Member> findAll(){ //모든 멤버 가져오기
        return em.createQuery("SELECT m FROM Member m", Member.class)
                .getResultList();
    }
    public List<Member> findByName(String name){ //이름으로 멤버를 가져오기
        return em.createQuery("SELECT m FROM Member m WHERE m.name=:name", Member.class)
                .setParameter("name",name)
                .getResultList();
    }



}
