package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 중요!
// readOnly = true 시, JPA가 조회할 때 성능을 최적화함.
@RequiredArgsConstructor // final이 붙은 field만 가진 생성자 생성, 필드 주입 대신 생성자 주입을 사용하자
// 왜? 변경 불가능한 안전한 객체 생성 가능, final 키워드를 추가하여 컴파일 시점에 memberRepository를 설정하지 않는 오류 체크 가능
public class MemberService {

	private final MemberRepository memberRepository;

	/**
	 * 회원가입
	 */
	@Transactional // readOnly=true 이면 쓰기가 안되므로 따로 설정, 우선 적용됨
	public Long join(Member member) {
		validateDuplicateMember(member); //중복 회원 검증
		memberRepository.save(member);
		return member.getId();
	}

	// Q. 어? 이래도 문제가 될텐데? WAS가 동시에 여러개가 뜨잖아요
	// memberA 동시 join 시, 동시에 validateDuplicateMember 로직을 호출해서 타게 될 수 있음.
	// business logic이 있다 하더라도, 실무에서는 한번 더 최후의 방어를 해야함.
	// 멀티 쓰레드 상황을 고려해서 database에 member의 name에 unique 제약 조건 추가.
	private void validateDuplicateMember(Member member) {
		List<Member> findMembers = memberRepository.findByName(member.getName());
		if (!findMembers.isEmpty()) { // 최적화 한다면 member 수를 세서 0이 아니면
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	/**
	 * 전체 회원 조회
	 */
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}
}
