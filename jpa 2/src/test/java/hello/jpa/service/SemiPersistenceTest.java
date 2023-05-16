package hello.jpa.service;

import hello.jpa.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class SemiPersistenceTest {
    @Autowired
    EntityManager em;
    @Test
    void semiPersistenceTest(){
        Member member = new Member();
        em.persist(member);
        em.flush();
        em.clear();
        Member sMember = new Member();
        sMember.setId(member.getId());
        em.persist(sMember);


    }
}
