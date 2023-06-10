package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

//    @Autowired  //@RequiredArgsConstructor가 있으면 주석처리한 코드들 안 써도 됨
//    public BasicItemController(ItemRepository itemRepository){  //생성자에 스프링 빈 주입
//        this.itemRepository = itemRepository;
//    }
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";//뷰가 있어야함 뷰를 찾아서 랜더링
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }
    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    //@PostMapping("/add")  //같은 url이지만 method로 역할 구분
    public String addItemV1(@RequestParam String itemName, @RequestParam int price, @RequestParam Integer quantity,
                            Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }


    /**
     ModelAttribute 사용

     - ModelAttribute는 자동으로 모델에 item을 넣어줌
     - @ModelAttribute("item")으로 사용했으므로 model에 item이라는 이름으로 들어감
     */
    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model){
        itemRepository.save(item);

        //model.addAttribute("item", item);  ModelAttribute는 자동으로 모델에 item을 넣어줌 따라서 생략 가능

        return "basic/item";
    }

    /**
     ModelAttribute name 생략 가능
     */
    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){  //modelattribute에 name을 넣지 않으면 첫글자를 소문자로 변경해서 model에 넣어줌

        itemRepository.save(item);

        return "basic/item";
    }


    /**
     ModelAttribute 전체 생략
     */
    //@PostMapping("/add")
    public String addItemV4(Item item){  //modelattribute를 생략할 수도 있음
        itemRepository.save(item);
        return "basic/item";
    }

    /**
     새로 고침 문제 해결
     */
    //@PostMapping("/add")
    public String addItemV5(Item item){  //새로고침 문제 해결
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();  //redirect 사용
    }

    /**
     고객에게 메시지까지 보여줌
     redirect 시 status 추가
     */
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){  //알림 추가, 파라미터 추가
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";  //redirect 사용 -> http://localhost:8080/basic/items/3?status=true  status가 생김
    }

    //수정
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }
    @PostMapping("/{itemId}/edit")
    public String editSave(@PathVariable long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";  //redirect 사용 -> url로 이동 controller부터 처음부터 호출됨
    }


    //테스트용 데이터 추가
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

}
