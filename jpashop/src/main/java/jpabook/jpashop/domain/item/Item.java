package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    //==비즈니스 로직==// 왜 엔티티에 비즈니스 로직이? 엔티티에 비즈니스 로직을 가지고, 서비스 계층은 단순히 엔티티에 필요한 요청을 위임하는 역할을 하는 것을 도메인 모델 패턴이라고 한다.
    public void addStock(int quantity) {  //재고 증가 or 주문 취소 시 재고 늘릴 때 사용
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {  //재고 감소, 재고가 부족하면 예외 발생
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0 ) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity -= quantity;
    }
}
//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="dtype")
//@Getter
//@Setter
//public abstract class Item {
//    @Id
//    @GeneratedValue
//    @Column(name="item_id")
//    private Long id;
//
//    private String name;
//    private int price;
//    private int stockQuantity;
//
//    @ManyToMany(mappedBy = "items")
//    private List<Category> categories = new ArrayList<Category>();
//
//    //==비즈니스 로직==//
//    public void addStock(int quantity) {  //재고 수량을 증가하는 로직
//        this.stockQuantity += quantity;
//    }
//    public void removeStock(int quantity) {  //재고 수량을 감소하는 로직
//        int restStock = this.stockQuantity - quantity;
//        if(restStock < 0) {
//            throw new NotEnoughStockException("need more stock");
//        }
//        this.stockQuantity = restStock;
//    }
//
//
//}
