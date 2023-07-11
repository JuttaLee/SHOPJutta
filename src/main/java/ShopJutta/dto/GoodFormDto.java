package ShopJutta.dto;

import ShopJutta.constant.GoodSellStatus;
import ShopJutta.entity.Good;
import ShopJutta.entity.GoodImg;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GoodFormDto {
    private Long id;

    @NotBlank(message = "상품 이름을 필수입력입니다.")
    private String GoodName;

    @NotNull(message = "상품 가격은 필수입력입니다.")
    private Integer GoodPrice;

    @NotBlank(message = "상품 상세 설명은 필수입력입니다.")
    private String GoodDetail;

    @NotNull(message = "상품 재고는 필수입력입니다.")
    private Integer GoodStockNumber;

    private GoodSellStatus goodSellStatus;
    private List<GoodImg> goodImgDtoList = new ArrayList<>();
    private List<Long> goodImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new modelMapper();

    public Good createGood() {
        return modelMapper.map(this, Good.class);
    }

    public static GoodFormDto of(Good good) {
        return modelMapper.map(good, GoodFormDto.class);
    }
}