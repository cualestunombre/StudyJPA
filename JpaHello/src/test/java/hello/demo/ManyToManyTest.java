package hello.demo;

import hello.demo.domain.Member;
import hello.demo.domain.Order;
import hello.demo.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class ManyToManyTest {
    @Autowired
    EntityManager em;


    @Test
    @Commit
    public void orderSearch(){
        Member member = em.find(Member.class,1L);
        Member member2 = em.find(Member.class,2L);
        log.info("---------");
        List<Order> orderList = member.getOrderList();
        for (Order order : orderList){
            log.info("id : {}",order.getId());
            order.setMember(member2);

        }
        log.info("---------");

    }

    @Test
    @Commit
    public void manyToManyTest(){
        Member member = new Member();
        Member member2 = new Member();
        member.setName("seokwoo");
        member2.setName("wooseok");
        em.persist(member);
        em.persist(member2);
        Product product = new Product();
        product.setName("macbook");
        em.persist(product);
        Order order = new Order();
        order.setProduct(product);
        order.setMember(member);
        em.persist(order);

    }

}
