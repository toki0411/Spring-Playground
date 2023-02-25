package hello.core;

@Configuration
public class AppConfig {
    @Bean
    public ChickenChef chickenChef() {
        return new ChickenChef(chickenRecipe());
    }
    @Bean
    public ChickenRecipe chickenRecipe() {return new FriedChickenRecipe();}
}
