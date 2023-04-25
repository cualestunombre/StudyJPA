package hello.demo;

import hello.demo.domain.Locker;
import hello.demo.domain.Person;
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
public class OneToOneTest {
    @Autowired
    EntityManager em;
    @Commit
    @Test
    public void makeTableForPerson(){
        Person person = new Person();
        person.setName("seokwoo");
        Locker locker = new Locker();
        locker.setName("5번 사물함");
        em.persist(locker);
        locker.setPerson(person);
        em.persist(person);

    }
}
