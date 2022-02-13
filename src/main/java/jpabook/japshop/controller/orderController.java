package jpabook.japshop.controller;

import jpabook.japshop.domain.Member;
import jpabook.japshop.domain.Order;
import jpabook.japshop.domain.item.Item;
import jpabook.japshop.repository.OrderSearch;
import jpabook.japshop.service.ItemService;
import jpabook.japshop.service.MemberService;
import jpabook.japshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class orderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;


    @GetMapping("/order")
    public String createForm(Model model) {

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String createForm(@RequestParam("memberId") Long memberId,
                             @RequestParam("itemId") Long itemId,
                             @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count);  //여러개의 상품으로 고칠수 도 잇다. 예제는 단순화 하기위해
        //실제 핵심 비지니스일때는 영한샘은 식별자만 넘기고 엔티티를 트랜젝션안에서 조회한다. 가급적이면 컨트롤러에서 조회는 상관없는데
        //식별자만 넘기고 핵심비지니스는 뒤에서 쳐리하는게 영속성 조회가 쉽다.
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);//단순한 기능은 화면에서 레파지토리 바로호출해도 ㄱㅊ
        model.addAttribute("orders", orders);

        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}