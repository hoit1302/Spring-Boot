package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable // jpa의 내장타입
@Getter // 값 타입은 변경 불가능하게 설계해야 함. setter 를 제공하지 않도록 해야함.
public class Address {
	private String city;
	private String street;
	private String zipcode;

	// 임베디드 타입은 기본 생성자를 public 또는 protected로 설정해야 한다.
	// 이런 제약을 두는 이유는 JPA 구현 라이브러리가 객체를 생성할 때
	// 리플랙션, 프록시 등과 같은 기술을 사용할 수 있도록 지원해야하기 때문이다.
	protected Address() {	}

	// 생성 시에만 값이 setting 되도록.
	public Address(String city, String street, String zipcode) {
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}
}
