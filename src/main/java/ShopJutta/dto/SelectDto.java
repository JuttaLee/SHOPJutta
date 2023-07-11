package ShopJutta.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectDto {
    @NotNull(message = "상품 번호는 필수입력입니다.")
    private Long GoodId;

    @Min(value = 1, message = "최소 상품 선택은 1개입니다.")
    @Max(value = 50, message = "최소 상품 선택은 15개입니다.")
    private int count;
}