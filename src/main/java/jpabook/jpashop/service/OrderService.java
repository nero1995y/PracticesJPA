package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성 실제는 새로입력하게해야한다.
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        // 예제를 단순하 하기위해 하나만 했다. 멀티셀렉트를 해서 할수있는데 이건 개인이해보면 좋을것같다
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문저장   Order에 orderItems를 CascadType.ALL 이옵션떔에 오더를 펄시스트할때 나머지도 펄시스트 해준다. 강제로 그래서 자동으로 해준다
        // 범위에 대해서 고민을 많이 하신다. 오더가 오더아이템을 관리한다. 딜리버리, 오더아이템은 오더만 관리한다 라이프 사이클이 동일할때만 관리해주는게 좋다
        // 딜리버리가 다른곳에서도 참조한다면 캐스케이드를 쓰면 안된다.복잡하게 얽혀 돌아간다. 오더아이템도 마찬가지다. 별도 레파지토리를 만드는것이 낫다.
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     * */
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        //주문 취소
        order.cancel();
    }
    // 원래 DB중심이면 쿼리에 다 넣고 다시 삭제하고 했어야 했다. 그래서 서비스로직에서 비지니스로직을 했어야했다 이게 JPA 강점 더티체킹이다.
    // 오더에 상태를 바꿧고 오더 아이템에 스톡 컨티티가 원복이된다 이게장점아다. 총3개인듯? 다른게 오더아이템 오더

    //검색
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAll(orderSearch);
    }
}
