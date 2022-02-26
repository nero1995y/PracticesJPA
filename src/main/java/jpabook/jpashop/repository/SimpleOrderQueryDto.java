package jpabook.japshop.repository;

import jpabook.japshop.domain.Address;
import jpabook.japshop.domain.Order;
import jpabook.japshop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleOrderQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public SimpleOrderQueryDto(Order order) {
        orderId = order.getId();
        name = order.getMember().getName();  //LAZY 초기화
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        address = order.getDelivery().getAddress(); //LAZY 초기화

    }

}
