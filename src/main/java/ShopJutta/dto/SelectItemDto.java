package ShopJutta.dto;

import ShopJutta.entity.SelectItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectItemDto {
    public SelectItemDto(SelectItem selectItem, String imgUrl) {
        this.GoodName = selectItem.getGood().getGoodName();
        this.Goodcount = selectItem.getSelectCount();
        this.SelectPrice = selectItem.getSelectPrice();
        this.imgUrl = imgUrl;
    }
    
    private String GoodName;        // 상품 이름
    private int Goodcount;          // 상품 선택 수량
    private int SelectPrice;        // 상품 주문 금액
    private String imgUrl;          // 상품 이미지 경로
}