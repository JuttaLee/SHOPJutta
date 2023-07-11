package ShopJutta.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GoodDto {
    private Long id;
    private String GoodItemName;
    private Integer GoodPrice;
    private String GoodItemDetail;
    private String GoodSellStatCd;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}