package ShopJutta.dto;

import ShopJutta.entity.GoodImg;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodImgDto {
    private Long id;
    private String GoodImgName;
    private String GoodOriginalImgName;
    private String GoodImgUrl;
    private String GoodRepImgYn;

    private static Modelmapper modelmapper = new ModelMapper();

    public static GoodImgDto of(GoodImg goodImg) {
        return modelmapper.map(goodImg, GoodImgDto.class);
    }
}