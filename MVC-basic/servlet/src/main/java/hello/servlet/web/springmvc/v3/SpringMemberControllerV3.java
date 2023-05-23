package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* MVC 주로 사용하는 방식
* Model 도입
* ViewName 직접 반환
* @RequestParam 사용
* @RequestMapping -> @GetMapping, @PostMapping
* */
@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @GetMapping(value="/new-form")
    public String newForm() {
        return "new-form";
    }

    @PostMapping(value="/save")
    public String save(@RequestParam("username") String username,
        @RequestParam("age") int age, Model model){  //model이 파라미터

        Member member = new Member(username, age);
        memberRepository.save(member);

        model.addAttribute("member", member);
        return "save-result";
    }

    @GetMapping
    public String members(Model model) {  //model이 파라미터
        List<Member> members = memberRepository.findAll();

        model.addAttribute("members", members);
        return "members";
    }
}
