package hello.jpa;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import hello.jpa.domain.*;
import hello.jpa.repository.ItemRepository;
import hello.jpa.repository.MemberRepository;
import hello.jpa.service.ItemService;
import hello.jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.EntityManager;

@SpringBootApplication
public class JpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}
	@Bean
	Hibernate5Module hibernate5Module(){
		Hibernate5Module hibernate5Module = new Hibernate5Module();
		return hibernate5Module;
	}


}
@Component
class InitDB{

	@Autowired
	private InitService initService;

	@EventListener(ApplicationReadyEvent.class)
	public void init(){
		initService.dbInit1();
		initService.dbInit2();
	}

	@Component
	@Transactional
	@RequiredArgsConstructor
	static class InitService{
		private final EntityManager em;
		public void dbInit1(){
			Member member = createMember("userA","서울","11","1");
			em.persist(member);

			Book book1 = createBook("JPABOOK1",10000,100);
			Book book2 = createBook("JPABOOK2",20000,100);
			em.persist(book1);
			em.persist(book2);

			OrderItem orderItem1 = OrderItem.createOrderItem(book1,10000,1);
			OrderItem orderItem2 = OrderItem.createOrderItem(book2,20000,2);
			Order order = Order.createOrder(member,createDelivery(member),orderItem1,orderItem2);
			em.persist(order);
		}

		public void dbInit2(){
			Member member = createMember("userB","울산","중구", "234");
			em.persist(member);

			Book book1 = createBook("SPRING BOOK",20000,200);
			em.persist(book1);

			Book book2 = createBook("SPRING BOOK2",30000,100);
			em.persist(book2);

			Delivery delivery = createDelivery(member);

			OrderItem orderItem1 = OrderItem.createOrderItem(book1,20000,3);
			OrderItem orderItem2 = OrderItem.createOrderItem(book2,30000,4);
			Order order = Order.createOrder(member,delivery,orderItem1,orderItem2);
			em.persist(order);
		}
		private Member createMember(String name,String city, String street,String zipcode){
			Member member = new Member();
			member.setName(name);
			member.setAddress(new Address(city,street,zipcode));
			return member;
		}
		private Book createBook(String name,int price, int stockQuantity){
			Book book = new Book();
			book.setName(name);
			book.setPrice(price);
			book.setStockQuantity(stockQuantity);
			return book;
		}
		private Delivery createDelivery(Member member){
			Delivery delivery = new Delivery();
			delivery.setAddress(member.getAddress());
			return delivery;
		}
	}




}