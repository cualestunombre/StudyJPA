package hello.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="ORDER_TABLE")
public class Order{
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    Product product;

    @ManyToOne
    @JoinColumn(name="MEMBER_ID")
    Member member;

    Integer orderAmount;
    LocalDateTime orderDate;

}
