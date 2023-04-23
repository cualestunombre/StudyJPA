package hello.demo.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/*
기본 생성자 필수(public)
저장할 필드 final하지 말 것
 */
@Entity
@Data
@Table(name="member")
public class Member {
    @Id
    private Long id;
    @Column(name="Hey")
    private String name;

    public Member(){}
    public Member(Long id,String name){
        this.id=id;
        this.name=name;
    }
}
