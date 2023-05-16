package hello.jpa.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto{
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderDto(Order order){
        orderId = order.getId();
        name = order.getMember().getName();
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        address = order.getDelivery().getAddress();
    }
    public OrderDto(Long orderId,String name,LocalDateTime orderDate,OrderStatus orderStatus,
    Address address){
        this.orderId=orderId;
        this.name=name;
        this.orderDate=orderDate;
        this.orderStatus=orderStatus;
        this.address=address;
    }
}