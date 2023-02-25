package hello.core.chicken;

import org.springframework.stereotype.Component;

//@Component
public class FriedChickenRecipe implements ChickenRecipe{
    public FriedChickenRecipe() {
        System.out.println("후라이드~");
    }
}
