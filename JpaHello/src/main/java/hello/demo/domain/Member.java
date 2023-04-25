package hello.demo.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
기본 생성자 필수(public)
저장할 필드 final하지 말 것
 */
@Entity
@Data
@Table(name="member")
public class Member {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name="USER_NAME")
    private String name;

    @OneToMany(mappedBy = "member")
    private List<Order> orderList = new ArrayList<>();

    public Member(){}
    public Member(Long id,String name){
        this.id = id;
        this.name = name;
    }


}
