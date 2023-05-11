package hello.demo;

import hello.demo.domain.MemberDto;
import hello.demo.domain.project.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Transactional
@Slf4j
@SpringBootTest
public class JPQLTest {

    @Autowired
    EntityManager em;



    @Test
    void caseTest(){
        String jpql = "SELECT " +
                "(CASE WHEN m.id <=30 then 'idiot' " +
                "WHEN m.id <= 60 then 'normal' " +
                "ELSE 'genious' " +
                "END) AS data " +
                "FROM Member m";
        List<String> data = em.createQuery(jpql,String.class).getResultList();
        for(String s:data){
            System.out.println(s);
        }

        List<Tuple> result = em.createQuery("SELECT m.orderList FROM Member m", Tuple.class).getResultList();
        System.out.println("size" + Integer.toString(result.size()));
        for(Tuple r:result){
            System.out.println(r);
        }

    }
    @Test
    void impicitJoin(){
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        Member m1 = em.createQuery("SELECT o.member FROM Orders o INNER JOIN o.member m WHERE o.id=101 ", Member.class).getSingleResult(); //inner join
        Member m2 = em.createQuery("SELECT o.member FROM Orders o WHERE o.id=101 ",Member.class).getSingleResult(); //묵시적 조인
        if(m1==m2){
            System.out.println("same");
        }
        else{
            System.out.println("notSame");
        }
        List<Member> members = em.createQuery("SELECT m FROM Member m inner join Orders o on m = o.member", Member.class).getResultList();
        System.out.println(members.size());

    }

    @Test
    @Commit
    void MemberJpqlTest(){
        for(int i=0;i<100;i++){
            Member m = new Member();
            m.setName(Integer.toString(i));
            m.setType(Type.USER);
            em.persist(m);
        }
        Member member = em.find(Member.class,1L);
        Orders order = new Orders();
        order.setMember(member);
        member.getOrderList().add(order);
        em.persist(order);
        Orders sOrder = new Orders();
        sOrder.setMember(em.find(Member.class,2L));
        em.persist(sOrder);

        em.flush();
        em.clear();
        List<Member> memberList = em.createQuery("select m from Member m").getResultList();
        for (Member m : memberList){
            log.info("member name{}",m.getName());
        }
        Long sum = em.createQuery("select COUNT(m) from Member m",Long.class).getSingleResult();
        List<MemberDto>  dtoList= em.createQuery("SELECT new hello.demo.domain.MemberDto(m.id,m.name) FROM Member m ORDER BY m.id DESC",MemberDto.class)
                .setFirstResult(50)
                .setMaxResults(20)
                .getResultList();
        for(MemberDto dto : dtoList){
            System.out.println(dto.getId());
            System.out.println(dto.getName());
        }
        List<Member> members = em.createQuery("select o.member from Orders o right join o.member", Member.class)
                .getResultList();
        for (Member mem: members){
            System.out.println(mem+"zerox"+mem.getType().name());

        }
        List<Member> resultList = em.createQuery(
                        "select distinct m from Member m " +
                                "left join fetch m.orderList " +
                                "where m.id = :id", Member.class)
                .setParameter("id", 1L)
                .getResultList();
        System.out.println(resultList.size());

        Long i = em.createQuery("SELECT COUNT(m) FROM Member m, Orders o",Long.class).getSingleResult();
        System.out.println(i);

        List<Member> fM= em.createQuery("SELECT m from Member m WHERE m.id > (SELECT AVG(m.id) FROM m) AND m.type = hello.demo.domain.project.Type.USER",Member.class
        ).getResultList();
        //enum 사용
        System.out.println(fM.size());



    }

    @Test
    void inheritanceTest(){
        OrderItem orderItem = new OrderItem();
        Book book = new Book();
        book.setPrice(5000);
        orderItem.setItem(book);
        em.persist(book);
        em.persist(orderItem);
        String jpql = "SELECT i FROM Item i WHERE type(i) IN(Book)";

        System.out.println("inheritance");
        List<Item> orderItems = em.createQuery(jpql, Item.class).getResultList();
        for(Item o : orderItems){
            System.out.println(o.getName());
        }
        System.out.println("inheritance");

    }
}


