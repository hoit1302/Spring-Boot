package jpabook.jpashop.domain.item;

import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 한 테이블에 다 때려박는 것
// InheritanceType.TABLE_PER_CLASS : book, movie, album 3개의 table
// InheritanceType.JOINED : 가장 정교화된 style
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter // setter 제거
public abstract class Item {

	@Id
	@GeneratedValue
	@Column(name = "item_id")
	private Long id;

	private String name;
	private int price;
	private int stockQuantity; // 기본이 private이었다면 좋겠다는 강사님의 한마디 ㅎㅎ

	// domain 주도 개발...?
	// item 서비스에서 stockQuantity를 가져와서 값을 더해서 넣고 setStockQuantity() --> 이런 식으로 했을거임
	// 객체 지향적으로 생각해보면 데이터를 가지고 있는 쪽에 메서드를 두는 것이 응집력이 좋음.
	//==비즈니스 로직==//
	public void addStock(int quantity) {
		this.stockQuantity += quantity;
	}

	public void removeStock(int quantity) {
		int restStock = this.stockQuantity - quantity;
		if (restStock < 0) {
			throw new NotEnoughStockException("need more stock");
		}
		this.stockQuantity = restStock;
	}

}
