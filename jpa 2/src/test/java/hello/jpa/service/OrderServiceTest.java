package hello.jpa.service;

import hello.jpa.domain.*;
import hello.jpa.exception.NotEnoughStockException;
import hello.jpa.repository.OrderRepository;
import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void orderTest(){
        Member member = createMember();
        Item item = createBook("시골 JPA",10000,10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(),item.getId(),orderCount);


        Order getOrder = orderRepository.findOne(orderId);

        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(getOrder.getTotalPrice()).isEqualTo(10000*2);
        assertThat(item.getStockQuantity()).isEqualTo(8);
    }
    @Test
    public void moreThanStock(){
        Member member = createMember();
        Item item = createBook("시골 JPA",10000,10);

        int orderCount = 11;

        assertThatThrownBy(()->orderService.order(member.getId(),item.getId(),orderCount)).isInstanceOf(NotEnoughStockException.class);

    }

    @Test
    public void cancelOrder(){
        Member member = createMember();
        Item item = createBook("시골 JPA",10000,10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(),orderCount);

        orderService.cancelOrder(orderId);

        Order order = orderRepository.findOne(orderId);

        assertThat(item.getStockQuantity()).isEqualTo(10);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);

        List<Order> orders = orderRepository.findAllByString(new OrderSearch("회원1",null));
        assertThat(orders.size()).isEqualTo(1);

    }


    private Member createMember(){
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강남","123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity){
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }
}
