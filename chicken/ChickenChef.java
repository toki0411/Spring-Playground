package hello.core.chicken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class ChickenChef{
    private final ChickenRecipe chickenRecipe;

    //@Autowired
    public ChickenChef(ChickenRecipe chickenRecipe) {  //생성자
        this.chickenRecipe = chickenRecipe;
    }


    public String log() {
        return this.chickenRecipe.toString();
    }

}
