package ShopJutta.repository;

import ShopJutta.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    Basket findByMemberId(Long memberId);
}