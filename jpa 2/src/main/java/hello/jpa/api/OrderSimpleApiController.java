package hello.jpa.api;

import hello.jpa.domain.*;
import hello.jpa.repository.OrderSimpleQueryRepository;
import hello.jpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;
    @GetMapping("/api/v1/simple-orders") //아주 비효율적임
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch(null,null));
        for(Order order:all){
            order.getMember().getName();
            order.getDelivery().getAddress();
            order.getOrderItems().get(0); //지연로딩으로 가져옴
            //초기화하는 작업이 있어야 함
        }

        return all;
    }
    @GetMapping("/api/v2/simple-orders")
    public Result ordersV2(){
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> collection = orders.stream()
                .map(o->new OrderDto(o))
                .collect(Collectors.toList());

        return new Result<>(collection.size(),collection);

    }
    @GetMapping("/api/v3/simple-orders")
    public Result ordersV3(){
        List<Order> orders  = orderRepository.findAllWithMemberDelivery();
        List<OrderDto> collection = orders.stream()
                .map(o->new OrderDto(o))
                .collect(Collectors.toList());

        return new Result<>(collection.size(),collection);

    }
    @GetMapping("/api/v4/simple-orders")
    public Result ordersV4(){
        List<OrderSimpleQueryDto> collection = orderSimpleQueryRepository.findOrderDtos();
        return new Result(collection.size(),collection);
    }




}
