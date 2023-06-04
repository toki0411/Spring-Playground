package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {
    /*
    뷰 템플릿을 호출함
    1. ModelAndView를 반환하는 경우
    @ResponseBody가 없으면 response/hello로 뷰 리졸버가 실행되어서 뷰를 찾고 렌더링
    @ResponseBody가 있으면 뷰 리졸버를 실행하지 않고, HTTP 메시지 바디에 직접 response/hello라는 문자가 입력
     */
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1(){
        ModelAndView mav = new ModelAndView("response/hello").addObject("data", "hello!");
        return mav;
    }

    /*
    2. String을 반환하는 경우
    뷰의 논리 이름인 response/hello를 반환해도 뷰 템플릿이 렌더링 됨
    templates/response/hello.html
     */
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model){
        model.addAttribute("data", "hello-v2");
        return "response/hello";  //뷰로 감
    }


    /* 권장하지 않음
    3. Void 를 반환하는 경우
    @Controller를 사용하고 HttpServletResponse, OutputStream(Writer) 같은 HTTP 메시지 바디를 처리하는 파라미터가 없으면 요청 URL을 참고해서 논리 뷰 이름으로 사용
    요청 URL : /response/hello
    실행 : templates/resposne/hello.html
    */
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model){
        model.addAttribute("data", "hello-v3");
        //return "response/hello";  //경로 이름이랑 리턴이랑 같으면 없어도 됨
    }

    //v2정도가 적당하다고 함 

}
