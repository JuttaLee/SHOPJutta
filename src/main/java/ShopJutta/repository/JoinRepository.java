package ShopJutta.repository;

import ShopJutta.entity.Join;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinRepository extends JpaRepository<Join, Long> {
    Join findByEmail(String email);
}