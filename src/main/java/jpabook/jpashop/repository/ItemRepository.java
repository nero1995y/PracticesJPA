package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){ // 새로 생성된 객체
            em.persist(item);
        }else {
            em.merge(item);//업데이트라고 이해하면된다.
            //Item merge = em.merge(item);//업데이트라고 이해하면된다.
            //왼쪽은 넘어온것이고 오른쪽은 그냥 파라미터 객체 틀리다 주의
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
