package ShopJutta.repository;

import ShopJutta.dto.BasketDetailDto;
import ShopJutta.entity.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {
    BasketItem findByBasketIdAndGoodId(Long basketId, Long goodId);

    // BasketItem: bi, GoodImg: gi
    @Query("select new com.meetup.dto.BasketDetailDto(bi.id, g.goodName, g.price, bi.count, gi.count, gi.imgUrl)" +
            "from BasketItem bi, GoodImg gi " +
            "join bi.item b " +
            "where bi.basket.id = :basketId" +
            "and gi.Good.id = bi.good.id " +
            "and gi.repImgYn = 'Y'" +
            "select by bi.regTime desc"
    )
    List<BasketDetailDto> findBasketDtoList(Long basketId);
}