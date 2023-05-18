package hello.jpa.api;

import hello.jpa.domain.*;
import hello.jpa.repository.OrderQueryRepository;
import hello.jpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders") //좋지 않은 방법이다
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch(null,null));
        for(Order order: all){
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o->o.getItem().getName());
        }
        return all;
    }

    @GetMapping("/api/v2/orders")
    public Result ordersV2(){
        List<Order> orders = orderRepository.findAll();
        List<OrderDto>  orderDtos = orders.stream().map(o->new OrderDto(o)).collect(Collectors.toList());
        return new Result(orderDtos.size(),orderDtos);
    }

    @GetMapping("/api/v3/orders")
    public Result ordersV3(){
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDto> result = orders
                .stream()
                .map(o->new OrderDto(o))
                .collect(Collectors.toList());
        return new Result(result.size(),result);
    }
    @GetMapping("/api/v3.1/orders")
    public Result ordersV3_page(@RequestParam(defaultValue = "0") int offest, @RequestParam(defaultValue = "100")int limit){
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offest,limit);
        List<OrderDto> result = orders.stream()
                .map(o->new OrderDto(o))
                .collect(Collectors.toList());
        return new Result(result.size(),result);
    }
    @GetMapping("/api/v4/orders")
    public Result ordersV4(){
        List<OrderQueryDto> collection = orderQueryRepository.findOrderQueryDtos();
        return new Result(collection.size(),collection);
    }
    @GetMapping("/api/v5/orders")
    public Result ordersV5(){
        List<OrderQueryDto> collection = orderQueryRepository.findAllByDto_optimization();
        return new Result(collection.size(),collection);
    }
    @GetMapping("/api/v6/orders")
    public List<OrderQueryDto> ordersV6(){
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();

        return flats.stream()
                .collect(Collectors.groupingBy(
                        o -> new OrderQueryDto(
                                o.getOrderId(),
                                o.getName(),
                                o.getOrderDate(),
                                o.getOrderStatus(),
                                o.getAddress()
                        ),
                        Collectors.mapping(
                                o -> new OrderItemQueryDto(
                                        o.getOrderId(),
                                        o.getItemName(),
                                        o.getOrderPrice(),
                                        o.getCount()
                                ),
                                Collectors.toList()
                        )
                ))
                .entrySet()
                .stream()
                .map(e -> new OrderQueryDto(
                        e.getKey().getOrderId(),
                        e.getKey().getName(),
                        e.getKey().getOrderDate(),
                        e.getKey().getStatus(),
                        e.getKey().getAddress(),
                        e.getValue()
                ))
                .collect(Collectors.toList());
    }
}
