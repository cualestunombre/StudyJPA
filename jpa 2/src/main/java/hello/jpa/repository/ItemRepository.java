package hello.jpa.repository;

import hello.jpa.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item){ //item의 저장
        if(item.getId() == null){
            em.persist(item);
        }else{
            em.merge(item);
        }

    }

    public Item findOne(Long id){ //id로 item을 찾아온다
        return em.find(Item.class,id);
    }

    //모든 item을 찾아온다
    public List<Item> findAll(){
        return em.createQuery("SELECT i FROM Item i", Item.class).getResultList();
    }


}
