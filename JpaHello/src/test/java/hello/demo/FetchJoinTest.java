package hello.demo;

import hello.demo.domain.project.Member;
import hello.demo.domain.project.Orders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
public class FetchJoinTest {
    @Autowired
    EntityManager em;

    @Test
    void fetchJoinTest(){
        Orders order1 = new Orders();
        Orders order2 = new Orders();
        Member  member1 = new Member();
        Member member2 = new Member();
        Member member3 = new Member();
        order1.setMember(member1);
        order2.setMember(member2);
        em.persist(order1);
        em.persist(order2);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.flush();
        em.clear();

        System.out.println("지연로딩 발생");
        Orders foundOrder1 = em.find(Orders.class,order1.getId());
        System.out.println(foundOrder1.getMember().getName()); //LAZY LOADING 발동
        System.out.println(foundOrder1.getMember().getClass()); //프록시 객체임
        System.out.println("지연로딩 종료");

        System.out.println("즉시로딩 발생");
        Orders foundOrder2 = em.createQuery("SELECT o FROM Orders o JOIN FETCH o.member WHERE o.id=:id",Orders.class)
                .setParameter("id",order1.getId())
                .getSingleResult(); // 즉시로딩 발동
        System.out.println("즉시로딩 종료");

        System.out.println("컬렉션 즉시 로딩");
        List<Member> members = em.createQuery("SELECT m FROM Member m JOIN FETCH m.orderList", Member.class).getResultList();
        for(Member member:members){
            System.out.println(member.getOrderList().get(0).getClass());//프록시 객체가 아님
            System.out.println(member.getId());

        }
        System.out.println("컬렉션 즉시 로딩 종료");
    }

    @Test
    void joinList(){
        Member member1 = new Member();
        member1.setName("woo");
        Member member2 = new Member();
        member2.setName("seok");

        Orders order1 = new Orders();
        Orders order2 = new Orders();
        Orders order3 = new Orders();

        order1.setMember(member1);
        order2.setMember(member1);
        order3.setMember(member2);

        em.persist(member2);
        em.persist(member1);
        em.persist(order1);
        em.persist(order2);
        em.persist(order3);

        em.flush();
        em.clear();
        System.out.println("--join쿼리 실행--");
        List<Member> members = em.createQuery("SELECT DISTINCT m FROM Member m join fetch  m.orderList ", Member.class).getResultList();
        // 페이징 API를 사용할 수 없다
        //fetch join에 별칭을 주지 말 것
        for(Member member: members){
            System.out.println(member.getOrderList().size());
            //찾고자 하는 멤버는 2개인데 3개가 실행 됨 -> DISTINCT로 해결
        }
        System.out.println("--join쿼리 실행--");

    }
}
