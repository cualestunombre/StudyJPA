package hello.demo.domain.project;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class CategoryItem extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name="CATERGORY_ITEM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name="CATEGORY_ID")
    private Category category;

    @ManyToOne
    @JoinColumn(name="ITEM_ID")
    private Item item;

}
