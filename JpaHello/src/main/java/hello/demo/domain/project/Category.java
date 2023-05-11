package hello.demo.domain.project;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name="CATEGORY_ID")
    private Long id;

    String name;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    List<CategoryItem> categoryItemList = new ArrayList<>();

}
