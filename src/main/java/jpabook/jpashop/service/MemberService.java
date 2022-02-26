package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //JPA가 조회부분에 성능 최적화 한다.  //JPA 모든 동작은 트랜젝션안에 일어나야한다. 스프링이 제공하는 것을 권장
@RequiredArgsConstructor //final 인젝션 가지고 생성자 생성 @AllArgsConstructor
public class MemberService {

    //@Autowired
    private  final MemberRepository memberRepository; //필드 주입은 바꿀 수가 없다.
    // 세터주입은 런타임시점에 바꾸면 조립이후에 좋지않다.
    // final 권장드린다. 런타임시에 체크가능

    //@Autowired //생성자 주입 방식 제일 선호 ! TC 의존성을 명확하게 확인 할 수 있다.  //따로 선언안할시에 오토가 자동 선언
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
      * 회원가입
      */
    @Transactional //따로 설정하면 이게 먼저 선언됨
    public Long join(Member member){
        validateDuplicateMember(member); //중복 회원검증
        memberRepository.save(member);
        return member.getId();
    }

    public void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    // 동시에 멤버가 가입할시에 호출이된다. 비지니스상에는 데이터베이스에 맴버의 네임을 유니크로 설정해라.
    // 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long meberId){
        return memberRepository.findOne(meberId);
    }

    @Transactional //변경감지 영속성 컨텍스트
    public void update(Long id, String name) {
        //영속상태를 반환
        Member member = memberRepository.findOne(id);
        //스프링잉이 끝내는 시점에 커밋됨 그때 플러쉬하고 커밋함
        member.setName(name);
    }
}
