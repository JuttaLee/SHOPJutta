package ShopJutta.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketItemDto {
    @NotNull(message = "상품 번호는 필수입력입니다.")
    private Long BasketId;

    @Min(value = 1, message = "최소 1개의 상품을 담아주세요.")
    private int BasketCount;
}
