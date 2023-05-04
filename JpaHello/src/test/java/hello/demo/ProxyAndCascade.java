package hello.demo;

import hello.demo.domain.project.Delivery;
import hello.demo.domain.project.Member;
import hello.demo.domain.project.Orders;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Slf4j
@Transactional
public class ProxyAndCascade {
    @Autowired
    EntityManager em;


    //지연 로딩
    @Test
    @Commit
    public void makeTableForPerson(){
        Member member = new Member();
        member.setName("seokWoo");
        Delivery delivery = new Delivery();

        em.persist(delivery);
        em.persist(member);
        em.flush();
        em.clear();
        Member foundMember = em.find(Member.class,member.getId());


        log.info(foundMember.getClass().toString());


    }

    @Test
    @Commit
    public void Cascade(){
        Orders order = new Orders();
        Member member = new Member();
        order.setMember(member);

        em.persist(member);
        em.flush();
        em.clear();

    }
}
