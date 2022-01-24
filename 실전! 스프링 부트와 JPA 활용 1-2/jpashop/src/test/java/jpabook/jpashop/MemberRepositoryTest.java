package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
/*
// 간단한 예제에서 다뤘던 test

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

	@Autowired MemberRepository memberRepository;

	@Test
	@Transactional // spring 제공 어노테이션 사용 권장, 옵션 많고 spring 종속적으로 개발하기에
	// entity manager를 통한 모든 데이터 변경은 항상 트랜잭션 안에서 이루어져야하기 때문에
	// 해당 annotation이 없으면 error가 발생함
	@Rollback(false) // 항상 commit하지 않고 rollback하는데 test로 데이터를 유지시키고 싶다면!
	public void testMember() throws Exception {
		// given
		Member member = new Member();
		member.setUsername("memberA");

		// when
		Long savedId = memberRepository.save(member); // ctrl+alt+V (extract → variable), 암기할 단축키
		Member findMember = memberRepository.find(savedId); 

		// then
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		Assertions.assertThat(findMember).isEqualTo(member);
		// 같은 transaction 안에서 저장하고 조회하면 영속성 컨텍스트가 똑같겟죠?
		// 같은 영속성 컨텍스트 안에서는 id(식별자)가 같으면 같은 엔티티로 식별함
		// 그래서 select 쿼리조차 나가지 않은 걸 볼 수 있음.
	}

}
*/