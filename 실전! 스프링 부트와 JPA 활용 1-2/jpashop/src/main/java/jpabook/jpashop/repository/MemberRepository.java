package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor // spring-boot-data-jpa 라이브러리 사용 시 생성자 주입을 지원해줌.
// @Autowired가 @PersistenceContext 지원할 수 있도록.
public class MemberRepository {


//	@PersistenceContext // JPA 제공 표준 annotation, em injection, @Autowired가 아님.
//	private EntityManager em;

	private final EntityManager em;

	public void save(Member member) {
		em.persist(member); // 영속성 컨텍스트가 member 객체를 올림.
	}

	public Member findOne(Long id) {
		return em.find(Member.class, id);
	}

	public List<Member> findAll() {
		// Ctrl + Alt + N: inline 단축키
		// JPQL - entity 대상 쿼리. SQL - 테이블 대상 쿼리
		return em.createQuery("select m from Member m", Member.class).getResultList();
	}

	public List<Member> findByName(String name) {
		return em.createQuery("select m from Member m where m.name = :name", Member.class)
			.setParameter("name", name).getResultList();
	}
}
