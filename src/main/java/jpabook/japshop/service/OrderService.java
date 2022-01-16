package jpabook.japshop.service;

import jpabook.japshop.domain.Delivery;
import jpabook.japshop.domain.Member;
import jpabook.japshop.domain.Order;
import jpabook.japshop.domain.OrderItem;
import jpabook.japshop.domain.item.Item;
import jpabook.japshop.repository.ItemRepository;
import jpabook.japshop.repository.MemberRepository;
import jpabook.japshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성 실제는 새로입력하게해야한다.
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문저장   Order에 orderItems를 CascadType.ALL 이옵션떔에 오더를 펄시스트할때 나머지도 펄시스트 해준다. 강제로 그래서 자동으로 해준다
        // 범위에 대해서 고민을 많이 하신다. 오더가 오더아이템을 관리한다. 딜리버리, 오더아이템은 오더만 관리한다 라이프 사이클이 동일할때만 관리해주는게 좋다
        // 딜리버리가 다른곳에서도 참조한다면 캐스케이드를 쓰면 안된다.복잡하게 얽혀 돌아간다. 오더아이템도 마찬가지다. 별도 레파지토리를 만드는것이 낫다.
        orderRepository.save(order);

        return order.getId();
    }

    //취소

    //검색
}
