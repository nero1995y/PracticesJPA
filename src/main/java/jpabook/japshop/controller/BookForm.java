package jpabook.japshop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {

    private Long id;
    //책과 관련된 공통
    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;
}
