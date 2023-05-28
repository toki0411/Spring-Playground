package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());
    /*
    모든 HTTP METHOD를 허용
     */
    @RequestMapping("/hello-basic")  //
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }
    /*
    모든 HTTP METHOD를 허용
    */
    @GetMapping("/mapping-get")  //
    public String mappingGet() {
        log.info("mapping-get");
        return "ok";
    }
    /*
    PathVarable(경로 변수)
    변수명이 같으면 생략 가능
    @PathVariable("userId") String userId -> @PathVariable userId
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable String userId) {
        log.info("mappingPath userId={}", userId);
        return "ok";
    }
    /*
    미디어 타입 조건 매핑
    HTTP 요청의 content-type 헤더를 기반으로 미디어 타입으로 매핑
     */
    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String mappingConsumes(){  //
        log.info("mappingConsumes");
        return "ok";
    }
}
