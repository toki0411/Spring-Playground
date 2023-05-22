package hello.servlet.web.springmvc.v2;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/springmvc/v2/members")  //경로의 중복을 제거할 수 있다.
public class SpringMemberControllerV2 {
    private MemberRepository memberRepository = MemberRepository.getInstance();
    @RequestMapping("/new-form")
    public ModelAndView newForm() {
        return new ModelAndView("new-form");
    }

    @RequestMapping("/save")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        ModelAndView mv = new ModelAndView("save-result");  //jsp 이름, 현재 링크는 /save
        //mv.getModel().put("member", member);  //hashmap에 추가
        mv.addObject("member", member);  //윗 줄이랑 내용 같음
        return mv;
    }

    @RequestMapping("")
    public ModelAndView members() {
        List<Member> members = memberRepository.findAll();
        ModelAndView mv = new ModelAndView("members");  //jsp이름
        mv.addObject("members",members); //아래꺼랑 똑같은 코드
        // mv.getModel().put("members", members);  //hashmap에 추가,
        return mv;
    }
}
