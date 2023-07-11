package ShopJutta.repository;

import ShopJutta.entity.SelectItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectItemRepository extends JpaRepository<SelectItem, Long> {
}