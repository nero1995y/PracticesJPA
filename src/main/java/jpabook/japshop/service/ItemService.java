package jpabook.japshop.service;

import jpabook.japshop.domain.item.Item;
import jpabook.japshop.repository.ItemRepository;
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

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
