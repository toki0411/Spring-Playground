package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.Repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service  //비즈니스 로직, 트랜잭션 처리
@Transactional(readOnly = true)  //readonly = true, 데이터의 변경이 없는 읽기 전용 메서드에 사용, 영속성 컨텍스트를 플러시 하지 않음.
@RequiredArgsConstructor  //final의 필드만 가지고 생성자를 만들고, 빈 자동 주입
public class MemberService {
    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional  //읽고쓰겠다는 의미(readOnly=false)
    public Long join(Member member) {
        validateDuplicateMember(member);  //중복 회원 체크
        memberRepository.save(member);
        return member.getId();
    }

    //중복 회원 체크
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class MemberService {
//
//    private final MemberRepository memberRepository;
//
//    //회원 가입
//    @Transactional
//    public Long join(Member member) {
//        validateDuplicateMember(member);  //중복 회원 체크
//        memberRepository.save(member);
//        return member.getId();
//    }
//
//    private void validateDuplicateMember(Member member) {
//        List<Member> findMembers = memberRepository.findByName(member.getName());
//        if(!findMembers.isEmpty()){
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        }
//    }
//
//    //회원 전체 조회
//    public List<Member> findMembers(){
//        return memberRepository.findAll();
//    }
//
//    public Member findOne(Long memberId) {
//        return memberRepository.findOne(memberId);
//    }
//}
