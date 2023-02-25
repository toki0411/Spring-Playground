package hello.core.chicken;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//@Component
//@Primary
public class CurryChickenRecipe implements ChickenRecipe{
    public CurryChickenRecipe() {
        System.out.println("카레~");}
}
