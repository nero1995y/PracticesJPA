//package jpabook.japshop;
//
//import jpabook.japshop.domain.Member;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//@Repository
//public class MemberRepository {
//
//    @PersistenceContext  //jpa 등롣
//    private EntityManager em;
//
//    public Long save(Member member){
//        em.persist(member);
//
//        //왜 id만 반환할까? 커맨드와 쿼리랑 분리해라 ! 사이드이팩트를 방지하기위해!
//        return member.getId();
//    }
//
//    public  Member find(Long id){
//        return em.find(Member.class, id);
//    }
//}
