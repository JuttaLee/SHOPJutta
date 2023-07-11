package ShopJutta.entity;

import ShopJutta.constant.GoodSellStatus;
import ShopJutta.dto.GoodFormDto;
import ShopJutta.exception.OutOfStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "good")
@Getter
@Setter
@ToString
public class Good extends BaseEntity {
    @Id
    @Column(name = "good_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;        // 상품 코드

    @Column(nullable = false, length = 45)
    private String goodName;    // 상품 이름

    @Column(name = "price", nullable = false)
    private int goodPrice;      // 상품 비용

    @Column(nullable = false)
    private int goodStock;       // 상품 제고

    @Lob
    @Column(nullable = false)
    private String goodDetail;  // 상품 상세설명

    @Enumerated(EnumType.STRING)
    private GoodSellStatus goodSellStatus;      // 상품 판매 상태

    public void updateGood(GoodFormDto goodFormDto) {
        this.goodName = goodFormDto.getGoodName();
        this.goodPrice = goodFormDto.getGoodPrice();
        this.goodStock = goodFormDto.getGoodStockNumber();
        this.goodDetail = goodFormDto.getGoodDetail();
        this.goodSellStatus = goodFormDto.getGoodSellStatus();
    }

    public void removeStock(int selectCount) {
        int restStock = this.goodStock - goodStock;
        if(restStock < 0) {
            throw new OutOfStockException("상품 재고가 부족합니다.(현재 자리수량: " + this.goodStock + ")");
        }
        this.goodStock = restStock;
    }

    public void  addStock(int goodStockNumber) {
        this.goodStock += goodStockNumber;
    }
}