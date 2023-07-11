package ShopJutta.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BasketOrderDto {
    private Long BasketItemId;
    private List<BasketOrderDto> basketOrderDtoList;
}
