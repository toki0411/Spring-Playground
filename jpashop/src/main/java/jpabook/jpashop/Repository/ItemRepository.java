package jpabook.jpashop.Repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item) {
        if(item.getId()==null){  //중복체크
            em.persist(item);
        }
        else {
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}

//@Repository
//@RequiredArgsConstructor
//public class ItemRepository {
//
//    private final EntityManager em;
//
//    public void save(Item item) {
//        if (item.getId() == null) {  //id가 없다 -> 새로 생성한 객체이다.
//            em.persist(item);
//        } else {
//            em.merge(item);
//        }
//    }
//
//    public Item findOne(Long id) {
//        return em.find(Item.class, id);
//    }
//
//    public List<Item> findAll() {
//        return em.createQuery("select i from Item i", Item.class).getResultList();
//    }
//
//}
