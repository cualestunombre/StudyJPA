package hello.demo.domain.project;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="DTYPE")
public abstract class Item extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name="ITEM_ID")
    private Long id;


    private String name;
    private Integer price;
    private Integer stockQuantity;

    @OneToMany(mappedBy = "item",fetch = FetchType.LAZY)
    List<CategoryItem> categoryItemList = new ArrayList<>();

    @OneToMany(mappedBy = "item",fetch = FetchType.LAZY)
    List<OrderItem> orderItemList = new ArrayList<>();

}
