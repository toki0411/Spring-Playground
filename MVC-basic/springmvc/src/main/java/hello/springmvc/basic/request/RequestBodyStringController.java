package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    /*
    텍스트 메시지를 HTTP 메시지 바디에 담아서 전송
    HTTP 메시지 바디의 데이터를 InputStream을 사용해서 직접 읽을 수 있다.
     */
    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        response.getWriter().write("ok");
    }

    /*
    InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회
    OutputStream(Writer): HTTP 응답 메시지의 바디에 직접 결과 출력
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }

    /* 진짜 스프링
    <메시지가 바디를 통해서 넘어오는 경우 조회하는 방법>
    HttpEntity: HTTP header, body 정보를 편리하게 조회
    - 요청 파라미터를 조회하는 기능과 관계 없음 @RequestParam, @ModelAttribute X

    응답에도 HTTPEntity 사용 가능,
    - 메시지 바디 정보 직접 반환 (view 조회 X)
    - 헤더 정보를 포함할 수 있다.
    - HttpMessageConverter 사용

    HTTPEntity를 상속받은 RequestEntity, ResponseEntity도 사용가능
    */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);

        return new ResponseEntity<String>("ok", HttpStatus.CREATED);
    }

    /*
    @RequestBody
    - HTTP 메시지 바디 정보를 편리하게 조회할 수 있다.
    @ResponseBody
    - 메시지 바디 정보 직접 반환 (view 조회 X)
    - HttpMessageConverter 사용

    -Header 정보가 필요하면 HttpEntity를 사용
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV3(@RequestBody String messageBody) {
        log.info("messageBody={}", messageBody);
        return "ok";
    }
}
