package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

	@Id
	@GeneratedValue
	@Column(name = "category_id")
	private Long id;

	private String name;

	@ManyToMany
	// 중간 테이블로 mapping을 해주어야함.
	// 하지만 category_id와 item_id 이 외의 컬럼를 추가할 수 없음.
	// 이 때문에 사용하지 말자고 함.
	@JoinTable(name = "category_item",
		joinColumns = @JoinColumn(name = "category_id"),
		inverseJoinColumns = @JoinColumn(name = "item_id"))
	private List<Item> items = new ArrayList<>();

	// 상속 관계 표현
	// self로 양방향 관계를 걸었는데, 어렵게 생각하지 말고
	// 다른 entity 처럼 생각하고 각각 자식 입장에서, 부모 입장에서 만들면 된다.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Category parent;

	// 자식은 여러개 가질 수 있음.
	@OneToMany(mappedBy = "parent") // 너무 어렵
	private List<Category> child = new ArrayList<>();

	// jpa는 재미있는 게 foreign key 잡아준다고 함.
	// foreign key 걸어야하는지 마는지에 대해서는 시스템 특성에 따라 다르게 하면 됨.
	// 정확성이 중요하면 fk 걸고, 정확성보다는 실시간성이 중요하다고 하면 index 활용해서 하면 된다.

	//==연관관계 메서드==//
	public void addChildCategory(Category child) {
		this.child.add(child);
		child.setParent(this);
	}
}