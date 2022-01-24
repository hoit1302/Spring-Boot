package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

	private final EntityManager em;

	public void save(Item item) {
		if (item.getId() == null) { // jpa 저장 전까지는 id가 없음. 즉, 새로 생성한 객체임.
			em.persist(item);
		} else { // 이미 db 등록된 item이고, 강제로 update를 의미함.
			em.merge(item);
		}
	}

	public Item findOne(Long id) {
		return em.find(Item.class, id);
	}

	public List<Item> findAll() {
		return em.createQuery("select i from Item  i", Item.class).getResultList();
	}
}