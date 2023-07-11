package ShopJutta.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainItemDto {
    private Long id;            // 상품 번호
    private String qName;       // 상품 이름
    private String qDetail;     // 상품 상세설명
    private String qImageUrl;   // 상품 이미지 url
    private Integer qPrice;     // 상품 가격
}