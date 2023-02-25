package hello.core.chicken;

import hello.core.AppConfig;
import hello.core.AutoAppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class app {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
       // ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        ChickenChef chickenChef = ac.getBean(ChickenChef.class);

        String a = chickenChef.log();
        System.out.println("a = " + a);
    }
}
