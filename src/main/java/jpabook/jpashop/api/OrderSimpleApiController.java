package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne(ManyToOne, OneToOne) //성능 최적화
 * Order
 * Order -> Member  // ToOne 관겨
 * Order -> Delivery
 * */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private  final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all;

    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){
        //ORDER 2개
        //N + 1 -> 1 + 회원N + 배송N  = 5  1 + N(2)개  처음 쿼리가 나가고 n번 만큼 추가실행되는 것
        // 처음 order를 가져온다.
        //List<Order> orders = orderRepository.findAllByString(new OrderSearch());
                                      //orders
        List<SimpleOrderDto> result = orderRepository.findAllByString(new OrderSearch())
                .stream()
                .map(o -> new SimpleOrderDto(o))
                //.map(SimpleOrderDto::new)
                .collect(Collectors.toList());
                //.collect(toList());  // static import

        return result;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3(){

        List<SimpleOrderDto> orders = orderRepository.findAllWithMemberDelivery()
                .stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());


        return orders;
    }


    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order){
            orderId = order.getId();
            name = order.getMember().getName();  //LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); //LAZY 초기화

        }

    }
}
