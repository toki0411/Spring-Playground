package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ResponseBodyController {
    /*
    HTTP API, HTTP 메시지 바디에 응답 메시지 직접 입력
    HTML이나 뷰 템플릿을 사용하지 않고, HTTP 응답 메시지 바디에 직접 응답 메시지를 전달
    */
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");  //HttpServletResponse 객체를 통해서 HTTP 메시지 바디에 직접 메시지를 전달
    }

    /*
    HttpEntity, ResponseEntity(Http Status 추가)
    ResponseEntity는 HttpEntity를 상속 받았기 때문에, HTTP 메시지의 헤더, 바디 정보를 가지고 있음
    추가로 응답 코드를 설정할 수 있음
    */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    /*
    ResponseBody 사용 (권장)
    view를 사용하지 않고 HTTP 메시지 컨버터를 통해서 메시지를 직접 입력
     */
    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }

    /*
    string 아닌 JSON
    ResponseEntity를 반환하므로 HTTP 메시지 컨버터를 통해서 JSON형식으로 변환되어서 반환된다.
     */
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return new ResponseEntity<HelloData>(helloData, HttpStatus.OK);
    }

    /*
    @ResponseBody 사용
    @ResponseStatus(HttpStatus.OK)를 사용하면 응답 코드를 설정할 수 있다. (동적으로 변경은 불가능->ResponseEntity를 사용하면 가능)
     */
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return helloData;
    }

    /**
     * @Controller 대신에 @RestController 를 사용하면 모든 컨트롤러에 @ResponseBody가 적용된다.
     * 따라서 REST API를 생성할 때 사용한다.
     * @RestController 안에 @ResponseBody가 적용되어 있다.
     */
}
