package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")  //싱글 테이블 될 때 구분을 위해 넣음
@Getter
@Setter
public class Book extends Item {
    private String author;
    private String isbn;
}
