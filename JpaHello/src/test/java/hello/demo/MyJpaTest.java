package hello.demo;

import hello.demo.domain.Member;
import hello.demo.domain.People;
import hello.demo.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Slf4j
public class MyJpaTest {
    @Autowired
    MemberRepository repo;
    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void save(){
        Member member = new Member(1L,"seok");
        repo.save(member);
        Member foundMember = repo.find(1L).orElseThrow();
        Assertions.assertThat(foundMember).isEqualTo(member);
    }
    @Test
    @Transactional
    void update(){
        Member member = new Member(1L,"seokwoo");
        repo.save(member);
        member.setName("wooseok");
        Member foundMember = repo.find(1L).orElseThrow();
        Assertions.assertThat(foundMember.getName()).isEqualTo("wooseok");

    }

    @Test
    @Transactional
    void findAll(){
        Member member1 = new Member(1L,"seokwoo");
        Member member2= new Member(2L,"seokwoo");
        Member member3 = new Member(3L,"seokwoo");
        repo.save(member1);
        repo.save(member2);
        repo.save(member3);
        List<Member> members = repo.findAll();
        for (Member m : members){
            log.info("id:{} name:{}",m.getId(),m.getName());
        }
    }
    @Test
    @Transactional
//    @Commit
    void mergeTest(){
        Member member = new Member(1L,"heokwoo");
        Member merged = repo.merge(member);
        merged.setName("dok");
        member.setName("do");


    }

    @Test
    @Transactional
    @Commit
    void PeopleTest(){
        People people1 = new People();
        People people2 = new People();
        People people3 = new People();
        People people4 = new People();
        People people5 = new People();
        People people6 = new People();
        People people7 = new People();
        people1.setUsername("a");
        people2.setUsername("b");
        people3.setUsername("c");
        people4.setUsername("d");
        people5.setUsername("e");
        people6.setUsername("f");
        people7.setUsername("g");
        //한번에 50개씩 가져온다 디비 접근을 줄인다! -> 연속한 id를 가짐!!
        em.persist(people1);
        em.persist(people2);
        em.persist(people3);
        em.persist(people4);
        em.persist(people5);
        em.persist(people6);
        em.persist(people7);
    }




}
