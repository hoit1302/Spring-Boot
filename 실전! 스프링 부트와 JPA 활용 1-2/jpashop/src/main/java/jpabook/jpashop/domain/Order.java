package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
	@Id
	@GeneratedValue
	@Column(name = "order_id") // 이런 식으로 설정하는 걸 선호하는 편.
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id") // mapping을 무엇으로?
	private Member member;

	// Order를 persist하면 OrderItem persist도 강제로 날려줌.
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();

	// Order를 persist하면 Delivery entity도 persist 됨.
	@OneToOne(fetch = LAZY, cascade = CascadeType.ALL) // 1:1의 경우 FK는 어디에 두어도 상관 없음.
	// 이 경우엔, 주문 → 배달내역 확인하지만 배달에서 주문을 참조하지는 않음.
	// order 쪽에 연관관계 주인을 둠.
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;

	// private Date date; // 과거, 날짜 관련 annotation mapping을 추가로 해야함.
	private LocalDateTime orderDate; // java8, hibernate가 알아서 자동으로 지원을 해줌.

	private OrderStatus status; // 주문상태 [ORDER, CANCEL]

	//==연관관계 메서드==//
	// 일반적으로 연관 관계의 주인 쪽에 추가하는 것이 좋음.

	// business logic에서 아래와 같이 하지 말고 원자적으로 묶어서 실수 없도록.
	// order.setMember(member);
	// member.getOrders().add(member);
	public void setMember(Member member) {
		this.member = member;
		member.getOrders().add(this);
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
	}

	//==생성 메서드==//
	// 주문 생성 시 오류를 줄여주고 변경 사항이 있어도 해당 메서드 하나만 변경하면 된다.
	public static Order createOrder(
		Member member, Delivery delivery, OrderItem... orderItems) {
		Order order = new Order();
		order.setMember(member);
		order.setDelivery(delivery);
		for (OrderItem orderItem : orderItems) {
			order.addOrderItem(orderItem);
		}
		order.setStatus(OrderStatus.ORDER);
		order.setOrderDate(LocalDateTime.now());
		return order;
	}

	//==비즈니스 로직==//
	/**
	 * 주문 취소
	 */
	public void cancel() {
		if (delivery.getStatus() == DeliveryStatus.COMP) {
			throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
		}

		this.setStatus(OrderStatus.CANCEL);
		for (OrderItem orderItem : orderItems) {
			orderItem.cancel(); // 주문 취소 시 각각의 주문 아이템에도 취소
		}
	}

	//==조회 로직==//
	/**
	 * 전체 주문 가격 조회
	 */
	public int getTotalPrice() {
		/*
		 int totalPrice = 0;
		 for (OrderItem orderItem : orderItems) {
		 totalPrice += orderItem.getTotalPrice();
		 }
		 return totalPrice;
		*/
		// java stream, lambda를 사용하여 깔끔하게 작성할 수 있음.
		return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
	}
}
