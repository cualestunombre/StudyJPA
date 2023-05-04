package hello.demo.domain.family;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
@Entity
@Getter
@Setter
public class Child {
    @Id
    @GeneratedValue
    private Long childId;

    private String name;

    @OneToOne(mappedBy = "child")
    private ParentChild parentChild;


}
