package hello.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Player {
    @Id
    @Column(name="PLAYER_ID")
    private String id;

    private String username;

    @ManyToOne
    @JoinColumn(name="TEAM_ID")
    private Team team;

    public void setTeam(Team team){
//        if(this.team != null){
//            this.team.getPlayers().remove(this);
//        }
//        this.team=team;
//        team.getPlayers().add(this);
        this.team=team;

    }



}
