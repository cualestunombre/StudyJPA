package hello.jpa.repository;

import hello.jpa.domain.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {
    private final EntityManager em;
    public List<OrderSimpleQueryDto> findOrderDtos(){
        String jpql = "SELECT new hello.jpa.domain.OrderSimpleQueryDto(o.id,m.name,o.orderDate,o.status,d.address) " +
                "FROM Order o JOIN o.member m JOIN  o.delivery d";
        return em.createQuery(jpql, OrderSimpleQueryDto.class).getResultList();
    }

}
