package hello.jpa.repository;

import hello.jpa.domain.Order;
import hello.jpa.domain.OrderDto;
import hello.jpa.domain.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    } //주문 저장

    public Order findOne(Long id){
        return em.find(Order.class,id);
    } //id로 주문가져오기
    public List<Order> findAll(){
        String jpql = "SELECT o FROM Order o";
        return em.createQuery(jpql,Order.class).getResultList();
    }
    public List<Order> findAllWithMemberDelivery(){
        String jpql = "SELECT o FROM Order o JOIN FETCH o.member JOIN FETCH o.delivery ";
        return em.createQuery(jpql,Order.class).getResultList();

    }
    public List<OrderDto> findOrderDtos(){
        String jpql = "SELECT new hello.jpa.domain.OrderDto(o.id,m.name,o.orderDate,o.status,d.address) " +
                "FROM Order o JOIN o.member m JOIN  o.delivery d";
        return em.createQuery(jpql, OrderDto.class).getResultList();
    }
    public List<Order> findAllByString(OrderSearch orderSearch){ //조건을 더해서 주문정보를 가져온다
        String jpql = "SELECT o FROM Order o join o.member m"; // 멤버가 있는 주문을 불러온다
        boolean isFirstCondition = true;

        if(orderSearch.getOrderStatus()!=null){
            if(isFirstCondition){
                jpql += " where";
                isFirstCondition=false;
            }else{
                jpql+=" and";
            }
            jpql+=" o.status =:status";
        }
        if(StringUtils.hasText(orderSearch.getMemberName())){
            if(isFirstCondition){
                jpql+=" where";
                isFirstCondition=false;
            }else{
                jpql+=" and";
            }
            jpql += " m.name like :name";
        }
        TypedQuery<Order> query = em.createQuery(jpql, Order.class).setMaxResults(1000);
        if(orderSearch.getOrderStatus()!=null){
            query = query.setParameter("status",orderSearch.getOrderStatus());
        }
        if(StringUtils.hasText(orderSearch.getMemberName())){
            query = query.setParameter("name",orderSearch.getMemberName());
        }
        return query.getResultList();
    }
}
