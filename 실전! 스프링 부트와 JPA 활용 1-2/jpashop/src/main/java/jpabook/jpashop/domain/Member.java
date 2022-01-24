package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

	@Id @GeneratedValue // sequence value를 쓰겠죠.
	@Column(name = "member_id")
	private Long id;

	private String name;

	@Embedded // 내장타입을 포함했음.
	private Address address;

	@OneToMany(mappedBy = "member") // mappedBy: 나는 이 연관관계의 주인이 아니에요
	// 내가 매핑하는 게 아니라 order에 의해서 매핑된 거울일 뿐이야
	// 나는 order 테이블의 member 필드에 의해서 연결된 거야!
	private List<Order> orders = new ArrayList<>();
}
