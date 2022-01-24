package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

	@Id @GeneratedValue
	@Column(name = "delivery_id")
	private Long id;

	@OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
	private Order order;

	@Embedded
	private Address address;

	@Enumerated(EnumType.STRING)
	// EnumType.ORDINAL: default 값임.
	// Column이 숫자, 1, 2, 3, 4, ... 로 들어감.
	// 중간에 다른 상태가 생기면 밀리면서 망함. 절대 쓰지 않기를 권고.
	private DeliveryStatus status; // READY, COMP
}
