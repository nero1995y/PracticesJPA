package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {
    //  추상 구현체로 만들려고 !
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();


    //== 비지니스로직 ==//
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if( restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
    //보통 개발할때는 여기서 서비스를 만들떄 stockQuantity 가져와서 값을 만든다음에 셋스톡 해서 넣었다.
    //비지니스가 여기 안에 있는것이 응집성이 훨씬 좋다. 여기에 있는것이 관리하기 좋기때문에 직접넣었다.
    //지금은 편의를 위해 setter 을 했지만 사실을 여기 애드 리무브 메소드안에서 처리해야한다.객체지향적처리
}
