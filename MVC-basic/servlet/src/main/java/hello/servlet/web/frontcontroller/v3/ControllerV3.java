package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {  //서블릿 기술 없앰 (서블릿 종속성 없앰)
    ModelView process(Map<String, String> paramMap);

}
