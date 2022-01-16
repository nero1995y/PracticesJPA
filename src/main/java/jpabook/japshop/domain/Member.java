package jpabook.japshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // 하나의 상품이 여러개의 주문을 하기때문에 //주인이 아니라서 읽기전용 !
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();


}
