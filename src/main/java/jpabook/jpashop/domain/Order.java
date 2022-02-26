package jpabook.japshop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id") //DBA 가 선호하는 네이밍
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") //포린키  //객체는 연관관계 주인을 고르는 팁은 포린키 있는 곳으로 !
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 order, cencel

    //연관관계 메서드 컨트롤하는 쪽이 들고 있는것이 좋다 양방향일때 좋음
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //== 생성메서드라고 한다. 주문 메서드 ==//
    // 실제 생성메서드는 훨씬 복잡하다. DTO도 넘어 올 수 있다.
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem: orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);  // 처음 상태는 강제해놓을것이다.
        order.setOrderDate(LocalDateTime.now());
        return order;
        // order 가연관관계를 걸고 스테이터스 데이크 타임까지 세팅이된다. 이런 스타일로 하는 것이좋다. 나중에 변경 가능성이 좋다

    }
    // 생성시점에 고치면 된다.
    //비지니스 로직

    /**
    * 주문 취소
    * */
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
                                    //this 를 써도 되는데 영한쌤은 잘안쓴다! 이미 보라색이니까 ! 강조할때나 이름이 똑같을때만 쓴다
            orderItem.cancel() ;//두번 생겼기때문에 두번해줘야함
        }
    }
    //== 조회 로직== //
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        //스트림으로도 바꿀수 있다.
        return totalPrice;
        //return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }



}
