package ShopJutta.dto;

import ShopJutta.constant.GoodSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodSearchDto {
    private String searchDateType;
    private GoodSellStatus goodSellStatus;
    private String searchBy;
    private String searchQuery = "";
}