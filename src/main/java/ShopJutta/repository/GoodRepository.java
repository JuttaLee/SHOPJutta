package ShopJutta.repository;

import ShopJutta.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoodRepository extends JpaRepository<Good,Long>, QuerydslPredicateExecutor<Good>,
        GoodRepositoryCustom {
    List<Good> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("select i from good i where i.GoodDetail like %:GoodDetail% order by i.price desc")
    List<Good> findByGoodDetail(@Param("goodDetail") String goodDetail);
}