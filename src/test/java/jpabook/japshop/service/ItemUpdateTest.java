package jpabook.japshop.service;

import jpabook.japshop.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

@SpringBootTest
public class ItemUpdateTest {
    @Autowired
    EntityManager em;

    @Test
    public void updateTest(){
        Book book = em.find(Book.class, 1L);

        //Tx
        book.setName("더티체킹");

        //변경감지 = dirty checking


    }
}
