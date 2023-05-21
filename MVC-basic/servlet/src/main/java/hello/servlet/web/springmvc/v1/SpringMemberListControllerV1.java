package hello.servlet.web.springmvc.v1;



import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
//Controller의 반환값은 modelAndview라는 것을 꼭 기억
@Controller
public class SpringMemberListControllerV1 {
    private MemberRepository memberRepository = MemberRepository.getInstance();
    @RequestMapping("/springmvc/v1/members")
    public ModelAndView process() {  //return값이 ModelAndView
        List<Member> members = memberRepository.findAll();
        ModelAndView mv = new ModelAndView("members");  //members -> jsp이름
        mv.addObject("members",members); //스프링의 ModelAndView를 통해 model 데이터를 추가할 때는 addObject()를 사용, 추가된 데이터는 뷰를 렌더링하는데 사용
       // mv.getModel().put("members", members);  //위랑 똑같은 코드, hashmap에 추가,
        return mv;
    }
}

