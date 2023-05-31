package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
public class RequestParamController {
    /*
    * GET, POST 와 같은 쿼리 파라미터에서 사용
    */

    /*
    @Controller일 때,
    반환 타입이 없으면서 응답에 값을 직접 집어넣으면 view 조회X
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    /*
    @RequestParam 사용
    - 파라미터 이름으로 바인딩
    @ResponseBody 사용
    - view 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    /*
    @RequestParam 사용
    HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /*
    @RequestParam 사용
    String, int 등의 단순 타입이면 @RequestParam도 생략 가능
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /*
    @RequestParam.required
    /request-param-required -> username이 없으므로 예외
    주의!
    /request-param-required?username= -> 빈문자로 통과
    주의!
    /request-param-required
    int age -> null을 int에 입력하는 것은 불가능, 따라서 Integer 변경해야 함(또는 다음에 나오는
    defaultValue 사용)
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required=true) String username,
            @RequestParam(required=false) int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /*
    @RequestParam
    - defaultValue 사용
    참고 : defaultValue는 빈 문자의 경우에도 적용
    /request-param-default?username=
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /*
    @RequestParam Map -> 파라미터의 값이 1개일 경우 (key = value) 사용
    @RequestParam MultiValueMap -> 파라미터의 값이 2개이상일 경우 (key = value1, value2) 사용
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(
            @RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    /*
    @ModelAttribute 사용
    객체에 값을 자동으로 넣어주는 @ModelAttribute 기능
    1. spring MVC는 HelloData 객체를 생성한다.
    2. 요청 파라미터의 이름으로 HelloData 객체의 프로퍼티를 찾는다.
    3. 해당 프로퍼티의 setter를 호출해서 파라미터의 값을 입력(바인딩)한다.
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
//        HelloData helloData = new HelloData();
//        helloData.setUsername(username);
//        helloData.setAge(age);  //ModelAttribute를 사용하면 이 코드들을 자동으로 처리해줌

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    /*
    @ModelAttribute 생략 가능
    * 주의할 점
    @ModelAttribute와 @RequestParam 모두 생략가능하므로 어떤 것을 생략가능한 것인지 알 수 없음
    스프링은 생략 시 다음과 같은 규칙을 적용한다.
    String , int , Integer와 같은 단순 타입 = @RequestParam 으로 인식
    나머지 = @ModelAttribute 으로 인식
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

}
