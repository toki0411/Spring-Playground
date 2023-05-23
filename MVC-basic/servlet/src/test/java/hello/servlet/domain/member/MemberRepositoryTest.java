import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemberRepositoryTest {
    //MemberRepository memberRepository = new MemberRepository();  //싱글톤이라 new 안됨 스프링 사용하면 싱글톤 보장됨
    MemberRepository memberRepository = MemberRepository.getInstance();

    void afterEach() {  //테스트 끝날 때마다 초기화
        memberRepository.clearStore();;
    }

    @Test
    void save(){
        //given
        Member member = new Member("hello", 20);

        //when
        Member savedMember = memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(savedMember.getId());
        assertThat(findMember).isEqualTo(savedMember);

    }

    @Test
    void findAll(){
        //given
        Member member1 = new Member("memer1", 20);
        Member member2 = new Member("member2", 30);

        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        List<Member> result = memberRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(member1, member2);
    }



}
