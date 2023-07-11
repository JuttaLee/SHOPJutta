package ShopJutta.repository;

import ShopJutta.entity.GoodImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodImgRepository extends JpaRepository<GoodImg, Long> {
    List<GoodImg> findByGoodIdSelectByIdAsc(Long goodId);
    GoodImg findByGoodIdAndRepImgYn(Long GoodId, String repImgYn);
}