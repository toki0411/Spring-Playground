package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyJsonController {

    // HTTP API에서 자주 사용하는 JSON
    // raw/json으로 메시지를 보냄
    private ObjectMapper objectMapper = new ObjectMapper();

    /*
    문자로 된 JSON 데이터를 Jackson 라이브러리인 objectMapper를 사용해서 자바 객체로 변환한다.
     */
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }

    /*
    @ReqeustBody를 이용해서 HTTP 메시지에서 데이터를 꺼내고 messageBody에 저장한다.
    문자로 된 JSON 데이터인 messageBody를 objectMapper를 통해서 자바 객체로 변환한다.
     */
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /*
    @RequestBody 생략 불가능 (@ModelAttribute가 적용되어 버림)
    @RequestBody HelloData data -> HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 우리가 원하는 문자나 객체 등으로 자동으로 변환해준다.

    * 참고
    HTTP 요청 시에 content-type이 application/json 이어야 JSON을 처리할 수 있는 HTTP 메시지 컨버터가 실행된다.
    */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData data) throws IOException {  //RequestBody 생략 시 modelattribute가 적용
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return "ok";
    }

    /*
    @RequestBody 대신 HttpEntity를 사용해도 된다.
     */
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity)  {
        HelloData data = httpEntity.getBody();
        log.info("username={}, age={}", data.getUsername(), data.getAge());

        return "ok";
    }

    /*
    @ResponseBody 적용
    - return이 객체 (메시지 바디 정보 직접 반환)
    - HttpMessageConverter 사용
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data)  {
        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return data;
    }

}
