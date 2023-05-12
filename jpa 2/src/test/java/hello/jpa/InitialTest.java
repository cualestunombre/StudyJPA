package hello.jpa;

import hello.jpa.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
public class InitialTest {
    @PersistenceContext
    EntityManager em;
    @Test
    @Commit
    void makeTable(){
        OrderItem orderItem = new OrderItem();
        Book book = new Book();
        book.setName("데미안");
        Order order = Order.createOrder(null,null,null);
        Delivery delivery = new Delivery();
        order.setDelivery(delivery);
        orderItem.setItem(book);
        order.addOrderItem(orderItem);
        em.persist(order);
        em.persist(book);


    }
}
