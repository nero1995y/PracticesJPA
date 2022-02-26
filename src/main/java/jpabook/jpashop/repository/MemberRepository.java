package jpabook.japshop.repository;

import jpabook.japshop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository //컴포넌트 스캔 컨테이너 등록
@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext //팩토리 자체도 직접 주입가능 //Spring dat JPA 가 제공한다 Autowier
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);   //왜 id만 반환할까? 커맨드와 쿼리랑 분리해라 ! 사이드이팩트를 방지하기위해!
    }
    //단건조회
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }
    //조회 JPQL 문법
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
    // 커멘트옵션 n 합치기 단축키임! 바인딩
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name" ,name)
                .getResultList();
    }
}
