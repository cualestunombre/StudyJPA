package hello.demo.domain.family;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Parent {
    @Id
    @GeneratedValue
    private Long parentId;


    @OneToOne(mappedBy = "parent")
    private ParentChild parentChild;

    private String name;


}
