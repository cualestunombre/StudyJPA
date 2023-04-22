package hello.demo.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="PEOPLE",uniqueConstraints = {@UniqueConstraint(name="NAME_AGE_UNIQUE",columnNames = {"NAME","AGE"})})
@SequenceGenerator(
        name="PEOPLE_SEQ_GENERATOR",
        sequenceName = "PEOPLE_SEQUENCES",
         initialValue = 1,allocationSize = 50
        )
@Getter
@Setter
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "PEOPLE_SEQ_GENERATOR")
    @Column(name="ID")
    private Integer id;

    @Column(name="NAME",nullable = false,length = 10)
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;



}
