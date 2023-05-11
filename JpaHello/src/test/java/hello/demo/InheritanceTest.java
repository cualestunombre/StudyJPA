package hello.demo;

import hello.demo.domain.project.Address;
import hello.demo.domain.project.Album;
import hello.demo.domain.project.Delivery;
import hello.demo.domain.project.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
@Transactional
@SpringBootTest
public class InheritanceTest{
    @Autowired
    EntityManager em;

    @Test
    void inheritanceSearch(){
        OrderItem orderItem = new OrderItem();
        Album album = new Album();
        album.setName("9번 교향곡");
        orderItem.setItem(album);
        em.persist(orderItem);
        em.persist(album);
        em.flush();
        em.clear();
        String jpql = "SELECT o FROM OrderItem o JOIN o.item i WHERE TYPE(i) IN (Album, Book)";
        //반드시 aliasing을 해줘야 type연산에 사용할 수 있다
        List<OrderItem> orderItems = em.createQuery(jpql, OrderItem.class).getResultList();
        for(OrderItem o:orderItems){
            System.out.println(o.getItem().getName());
        }
    }

    @Test
    void embededTest(){
        Address address = new Address("hongik","h","B");
        Delivery delivery = new Delivery();
        delivery.setAddress(address);
        em.persist(delivery);
        em.flush();
        em.clear();
        String jpql = "SELECT d FROM Delivery d WHERE d.address.street = 'hongik'";
        List<Delivery> deliveries = em.createQuery(jpql, Delivery.class).getResultList();
        System.out.println(deliveries.size());
    }



}