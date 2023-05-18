package hello.jpa.repository;

import hello.jpa.domain.OrderFlatDto;
import hello.jpa.domain.OrderItemQueryDto;
import hello.jpa.domain.OrderQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos(){
        List<OrderQueryDto> result = findOrders();
        result.forEach(o->{
            List< OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return result;
    }
    private List<OrderQueryDto> findOrders(){
        return em.createQuery("select new" +
                " hello.jpa.domain.OrderQueryDto(o.id,m.name,o.orderDate,o.status,d.address)" +
                " from Order o " +
                " join o.member m " +
                " join o.delivery d",OrderQueryDto.class).getResultList();
    }
    //객체 생성시에 컬렉션을 바로 넣을 수가 없다 + 엔티티도 바로 넣을 수 없다 (원시값을 넣어 줘야 한다)
    //넣을려면 넣을 수는 있는데 지연로딩 발생해서 성능 문제(n+1)
    private List<OrderItemQueryDto> findOrderItems(Long orderId){
        return em.createQuery(
                "select new" +
                        " hello.jpa.domain.OrderItemQueryDto(oi.order.id,i.name,oi.orderPrice,oi.count)" +
                        " from OrderItem  oi" +
                        " join oi.item i " +
                        " where oi.order.id =: orderId"
        ,OrderItemQueryDto.class)
                .setParameter("orderId",orderId)
                .getResultList();
    }

    public List<OrderQueryDto> findAllByDto_optimization(){
        List<OrderQueryDto> result = findOrders();

        Map<Long,List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(toOrderIds(result));
        result.stream().forEach(o->{
            o.setOrderItems(orderItemMap.get(o.getOrderId()));
        });
        return result;
    }

    private List<Long> toOrderIds(List<OrderQueryDto> result){
        return result.stream()
                .map(o->o.getOrderId())
                .collect(Collectors.toList());
    }

    private Map<Long,List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds){
        List<OrderItemQueryDto> orderItems = em.createQuery(
          "select new" +
                  " hello.jpa.domain.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                  " from OrderItem oi" +
                  " join oi.item i" +
                  " where oi.order.id in :orderIds"
        , OrderItemQueryDto.class)
                .setParameter("orderIds",orderIds)
                .getResultList();
        return orderItems.stream()
                .collect(Collectors.groupingBy((o)->o.getOrderId()));
    }

    public List<OrderFlatDto> findAllByDto_flat(){
        return em.createQuery(
                "select new hello.jpa.domain.OrderFlatDto(o.id,m.name,o.orderDate,o.status" +
                        ",d.address,i.name,oi.orderPrice,oi.count)" +
                        " from Order o " +
                        " join o.member m" +
                        " join o.delivery d " +
                        " join o.orderItems oi " +
                        " join oi.item i",OrderFlatDto.class
        ).getResultList();
    }
}
