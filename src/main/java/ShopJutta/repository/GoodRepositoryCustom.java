package ShopJutta.repository;

import ShopJutta.dto.GoodSearchDto;
import ShopJutta.dto.MainItemDto;
import ShopJutta.entity.Good;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoodRepositoryCustom {
    Page<Good> getAdminGoodPage(GoodSearchDto goodSearchDto, Pageable pageable);
    Page<MainItemDto> getMainItemPage(GoodSearchDto goodSearchDto, Pageable pageable);
}