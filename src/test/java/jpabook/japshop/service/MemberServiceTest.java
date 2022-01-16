package jpabook.japshop.service;

import jpabook.japshop.domain.Member;
import jpabook.japshop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class) junit4
@SpringBootTest //스프링컨테이너 안에서 돌리는것
@Transactional // 테스트 끝나고 롤백 시키는것
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @Rollback(value = false) //눈으로 확인하는것이 좋다.
    public void 회원가입() throws Exception{

        //given
        Member member = new Member();
        member.setName("kim");
        
        //when
        Long saveId = memberService.join(member);
        //then
        //em.flush();
        assertEquals(member, memberRepository.findOne(saveId));
    }
    @Test //(expected = IllegalStateException.class) 4 버전
    public void 중복_회원_예외() throws Exception{

        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");
        //then
        memberService.join(member1);

        //test옆에선언해줄수 있다.
        try {
            memberService.join(member2);

        }catch (IllegalStateException e){
            return;
        }

        //when
        fail("예외가 발생해야  한다.");

    }
}