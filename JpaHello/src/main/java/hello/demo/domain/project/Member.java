package hello.demo.domain.project;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
기본 생성자 필수(public)
저장할 필드 final하지 말 것
 */
@Entity
@Getter
@Setter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    @Column(name="USER_NAME")
    private String name;

    @Embedded
    private Address address;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "member" ,cascade=CascadeType.PERSIST)
    List<Orders> orderList = new ArrayList<Orders>();



    public Member(){}



}
