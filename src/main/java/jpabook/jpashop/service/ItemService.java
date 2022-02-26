package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)  //읽기전용 성능 조금향상
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;  //위임만 하는 클래스라 개발이 간편하게 끝났다.

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        //우선은 북 만해보자                                //UpdateItemDto dto
        Item findItem = itemRepository.findOne(itemId);

        //findItem.change(price, name, stockQuantity);
        //실제 업데이트 할때는 메소드를 만들어야한다. set,set,set 하면 안된다. 변경지점이 엔티티로 간다. 바뀌는 지점이 명확하게 설계
        //세터는 쓰지말자 !
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);

        //save를 호출할 필요가 없다. 값을 세팅한다음에 트랜젝션에 따라 플러시를 날려 영속성 컨텍스트에 변경 감지를하여 업데이트를 쳐진다.
        //이게 더 나은 방법이다.
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
