package hello.demo.domain.project;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Delivery  extends  BaseEntity{
    @Id
    @GeneratedValue
    @Column(name="DELIVERY_ID")
    private Long id;

    @Embedded
    private Address address;
    //부모인 배송이 사라지면 Delivery도 사라진다
    @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY,orphanRemoval = true)
    private Orders order;


}
