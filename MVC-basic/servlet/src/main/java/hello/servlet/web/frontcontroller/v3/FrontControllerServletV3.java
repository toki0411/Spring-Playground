package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name ="frontControllerServletV3", urlPatterns="/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerV3Map = new HashMap<>();

    public FrontControllerServletV3() {
        controllerV3Map.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerV3Map.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerV3Map.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServiceV3.service");
        String requestURI =request.getRequestURI();
        ControllerV3 controller = controllerV3Map.get(requestURI);  //컨트롤러가 찾아진다.
        if(controller ==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //paramMap(request)을 넘겨줘야 함
        Map<String, String> paramMap = createParamMap(request);

        ModelView mv = controller.process(paramMap);  //2,3단계: 컨트롤러 호출하고 ModelView 반환
        String viewName = mv.getViewName();//논리이름 new - form
        //"/WEB-INF/views/new-form.jsp" 생성
        MyView view = viewResolver(viewName);  //4. viewResolver 호출하고 MyView반환

        view.render(mv.getModel(), request, response);

    }
    private Map<String, String> createParamMap(HttpServletRequest request) {  //request를 map으로 만듬
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName,
                        request.getParameter(paramName)));
        return paramMap;
    }
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }


}
