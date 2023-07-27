package jpabook.jpashop.service;

import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)  //junit+ spring일 경우 추가
@SpringBootTest  //springboot를 띄운채로 테스트 할 경우 추가
@Transactional  //트랜잭션으로 실행하고 테스트가 끝나면 롤백해버림(이 어노테이션이 테스트 케이스에서 사용될 때만 롤백)
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    //@Rollback(value = false)  //롤백을 하지않고 커밋함(쿼리가보임) 롤백을 하면 쿼리가 안보임
    public void 회원가입() throws  Exception {
        //given
        Member member = new Member();
        member.setName("shin");

        //when
        Long savedId = memberService.join(member);

        //then
        //em.flush();  //db에 쿼리 날림
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)  //try catch의 역할. illegalstateexception이 발생하면 종료함.
    public void 중복_회원_예외() throws  Exception{
        //given
        Member member1 = new Member();
        member1.setName("shin");

        Member member2 = new Member();
        member2.setName("shin");

        //when
        memberService.join(member1);
        memberService.join(member2);  //예외 발생 필요 똑같은 이름이 두개기 때문

        //then
        fail("예외가 발생해야 한다."); //발생해야 할 경우에는 이걸로 처리 가능, 현재 발생할 예외는 illegalstateexception이므로 여기까지 오지 않음. 개발자들에게 여기까지 오면 안된다는것을 보여주는 의미로도 사용
    }


}