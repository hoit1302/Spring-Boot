package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 엔티티에 (복잡한) 핵심 비즈니스 로직을 몰아넣는 스타일을 domain model pattern이라고 부른다.
// 서비스 계층은 단순히 엔티티에 필요한 요청을 위임하는 역할을 하고 있는 것을 확인할 수 있다.
// 반대, 트랜잭션 스크립트 패턴: 엔티티에는 비즈니스 로직이 거의 없고 서비스 계층에서 대부분의 비즈니스 로직을 처리하는 것
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final MemberRepository memberRepository;
	private final OrderRepository orderRepository;
	private final ItemRepository itemRepository; // 의존을 많이 한다고 표현함.

	/**
	 * 주문
	 */
	@Transactional
	public Long order(Long memberId, Long itemId, int count) {

		//엔티티 조회
		Member member = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(itemId);

		//배송정보 생성
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());
		delivery.setStatus(DeliveryStatus.READY);

		//주문상품 생성
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

		//주문 생성, delivery와 orderItem도 persist 해야함.
		// 이 예제에서는 따로 persist 안하기 위해 Order에 cascade ALL 설정을 걸어두었음.

		// 주의점!
		// delivery는 Order만 참조하고, OrderItem도 Order에 의해서만 참조됨.
		// OrderItem이 누굴 참조하는 건 상관 없음.
		// 이러한 life cycle에 대해서 동일하게 관리를 하고 싶을 때 사용한다.
		Order order = Order.createOrder(member, delivery, orderItem);

		//주문 저장
		orderRepository.save(order);
		return order.getId();
	}

	/**
	 * 주문 취소
	 */
	@Transactional
	public void cancelOrder(Long orderId) {
		//주문 엔티티 조회
		Order order = orderRepository.findOne(orderId);
		//주문 취소
		order.cancel();

		// 어? 왜 로직이 이거밖에 없지?
		// db sql을 직접 다루는 라이브러리 (mybatis, jdbc template 등)은 update query를 직접 짜서 날려야 한다.
		// 하지만 JPA는 data만 바꾸면 알아서 바뀐 변경 포인트를 감지해 (dirty check) db에 update query를 날림.
		// 이것이 JPA의 큰 장점
	}
}