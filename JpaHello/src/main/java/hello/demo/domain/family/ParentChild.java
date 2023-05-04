package hello.demo.domain.family;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ParentChild {
    @Id
    @GeneratedValue
    Long Id;

    @OneToOne
    @JoinColumn(name = "PARENT_ID",unique = true)
    private Parent parent;

    @OneToOne
    @JoinColumn(name="CHILD_ID",unique = true)
    private Child child;


}
