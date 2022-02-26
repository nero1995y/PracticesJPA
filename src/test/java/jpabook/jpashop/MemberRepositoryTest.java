//package jpabook.japshop;
//
//
//import jpabook.japshop.domain.Member;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//class MemberRepositoryTest {
//
//    @Autowired MemberRepository memberRepository;
//
//
//    //@Test
//    @Transactional  //jpa는 트랜잭션널에서 동작하기에 오류가 안난다
//    @Rollback(value = false)
//    public void testMember() throws  Exception{
//
//        //given
//        Member member = new Member();
//      //  member.setUsername("memberA");
//
//        //when
//        Long saveId = memberRepository.save(member);
//        Member findMember = memberRepository.find(saveId);
//
//        //then
//        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//       // Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//        Assertions.assertThat(findMember).isEqualTo(member);
//        // 같은 영속성 컨텍스트있으면 1차캐시에서 같은 내용이다. 트루가 나올것이다.
//    }
//}