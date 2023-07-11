package ShopJutta.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketDetailDto {
    private Long BasketItemId;
    private String BasketName;
    private int BasketPrice;
    private int BasketAttend;
    private String BasketImgUrl;

    public BasketDetailDto(Long basketItemId, String basketName, int basketPrice, int basketAttend, String BasketImgUrl) {
        this.BasketItemId = basketItemId;
        this.BasketName = basketName;
        this.BasketPrice = basketPrice;
        this.BasketAttend = basketAttend;
        this.BasketImgUrl = getBasketImgUrl();
    }
}