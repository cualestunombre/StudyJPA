package hello.demo;

import hello.demo.domain.Member;
import hello.demo.domain.Player;
import hello.demo.domain.Team;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Slf4j
public class RelationTest {
    @Autowired
    EntityManager em;

//    @Transactional  //삽입 쿼리
//    @Commit
//    @Test
//    public void relationManyToOne(){
//        Team team1 = new Team();
//        team1.setId("team1");
//        team1.setName("팀1");
//        em.persist(team1);
//
//        Player player1 = new Player();
//        player1.setUsername("회원1");
//        player1.setId("member1");
//        player1.setTeam(team1);
//        em.persist(player1);
//
//        Player player2 = new Player();
//        player2.setUsername("회원2");
//        player2.setId("member2");
//        player2.setTeam(team1);
//        em.persist(player2);
//
//    }
//    @Test
//    @Transactional
    public void relationalSearchWithPersist(){
        Player player = em.find(Player.class,"member1");
        Team team = player.getTeam();
        log.info(team.getName());
    }
//    @Test
//    @Transactional
    public void relationalSearchWithJpql(){
        String jpql = "select m from Player m join m.team t where "+"t.name=:teamName";

        List<Player> resultList = em.createQuery(jpql,Player.class)
                .setParameter("teamName","팀1")
                .getResultList();
        for(Player player : resultList){
            log.info("id:{} name:{} team:{}",player.getId(),player.getUsername(),player.getTeam());
            player.setUsername("하하");
        }

    }
//    @Test
//    @Transactional
//    @Commit
    public void removeRelation(){
        Player player = em.find(Player.class,"member1");
        player.setTeam(null);

    }
    @Test
    @Transactional
    public void biDirection(){
        Team team = em.find(Team.class,"team1");
        List<Player> players = team.getPlayers();
        for(Player player:players){
            log.info("id:{} name:{}",player.getId(),player.getUsername());
        }
    }
    @Test
    @Transactional
    @Commit
    public void biDirectionInsert(){
        Team team = new Team();
        team.setName("팀2");
        team.setId("team2");
        em.persist(team);

        Player player = new Player();
        player.setId("player2");
        player.setUsername("seokwoo");
        player.setTeam(team);

        em.persist(player);

    }


}
