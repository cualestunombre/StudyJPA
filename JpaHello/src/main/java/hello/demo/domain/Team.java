package hello.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Team {
    @Id
    @Column(name="TEAM_ID")
    private String id;

    private String name;
    @OneToMany(mappedBy = "team")
    private List<Player> players = new ArrayList<>();

}
