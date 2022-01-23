package jpabook.japshop.domain;

import jpabook.japshop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;  //오더가 하나가 여러개의 오더 아이템을 갖는다 FK 가 여기 있기때문에 연관관계 주인 오더?

    private int orderPrice; //주문 가격
    private int count;  //주문 수량

    //컨밴션을 유지하기 위해 다른곳에 생성자를만드는것을 막는방법 @NoArgsConstructor(access = AccessLevel.PROTECTED)
//    protected OrderItem(){}

    //== 생성 메서드 ==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); //재고 수량 까기

        return orderItem;
    }

    //== 비지니스 로직 ==//
    public void cancel() {
        getItem().addStock(count); //재고 수량을 원복해준다.
    }
    //주문가격과 수령을 곱해야한다. 조회할떄는 !
    /**
     * 주문상품 전체 가격 조회
     * */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}