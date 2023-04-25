package hello.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne(mappedBy = "person")
    //실제 DB에 그런 거 없음 그래서 한번에 불러와야 함 -> 지연로딩 불가
    Locker locker;

    @Column(name = "NAME")
    private String name;
}
