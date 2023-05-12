package hello.jpa;

import hello.jpa.domain.Address;
import hello.jpa.domain.Book;
import hello.jpa.domain.Item;
import hello.jpa.domain.Member;
import hello.jpa.repository.ItemRepository;
import hello.jpa.repository.MemberRepository;
import hello.jpa.service.ItemService;
import hello.jpa.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class JpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}


}
@Component
class InitHandle{
	@Autowired
	private MemberService memberService;
	@Autowired
	private ItemService itemService;

	@EventListener(ApplicationReadyEvent.class)
	private void initDataInsertion(){

		Member member = new Member();
		member.setName("우석우");
		Address address = new Address("서울","종로","111");
		member.setAddress(address);
		memberService.join(member);
		Book book = new Book();
		book.setIsbn("1111");
		book.setPrice(5000);
		book.setStockQuantity(200);
		book.setAuthor("우석우");
		book.setName("스프링 완전정복");
		itemService.saveItem(book);

	}

}