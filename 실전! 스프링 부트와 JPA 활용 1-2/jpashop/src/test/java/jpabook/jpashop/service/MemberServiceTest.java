package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

// test case 작성 고수 되는 마법: Given, When, Then
// https://martinfowler.com/bliki/GivenWhenThen.html

@RunWith(SpringRunner.class) // Junit 실행할 때, spring이랑 엮어서 실행할래
@SpringBootTest // springboot를 띄운 상태로 테스트하기 위해서 (컨테이너)
@Transactional // 테스트 시, 테스트가 끝나면 rollback이 기본적
public class MemberServiceTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
 	@Autowired EntityManager em;

 	@Test // 그냥 돌리면 insert 문이 안 나감. test의 transactional이 rollback하기 때문
	@Rollback(false) // 방법1. commit을 해야 쿼리가 나가기 때문에 설정 변경
	// 2. 아니면 직접 em을 두고 em.flush() 를 통해 쿼리 나가는 것을 볼 수 있음. 하지만 rollback 됨.
	public void 회원가입() throws Exception {
		//Given
		Member member = new Member();
		member.setName("kim");
		//When
		Long saveId = memberService.join(member);
		//Then
		// em.flush();
		assertEquals(member, memberRepository.findOne(saveId));
	}

	@Test(expected = IllegalStateException.class)
	public void 중복_회원_예외() throws Exception {
		//Given
		Member member1 = new Member();
		member1.setName("kim");
		Member member2 = new Member();
		member2.setName("kim");
		//When
		memberService.join(member1);
		memberService.join(member2); // 똑같은 이름, 예외가 발생해야 한다.

// 		원래 이렇게 해야하는데 test annotation에 추가하고 지울 수 있다.
//		try {
//			memberService.join(member2);
//		} catch (IllegalStateException e) {
//			return;
//		}
		//Then
		// fail method 뜻: 해당 부분이 실행되면 안된다.
		fail("예외가 발생해야 한다."); // 비즈니스 메소드에 의해 해당 부분이 실행되면 안되고 예외가 발생해야 한다.
	}
}
