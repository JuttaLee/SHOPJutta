package ShopJutta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "good_img")
@Getter
@Setter
public class GoodImg extends BaseEntity {
    @Id
    @Column(name = "good_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String goodImgName;
    private String goodOriginalImgName;
    private String goodImgUrl;
    private String goodRepImgYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_id")
    private Good good;

    public void updateGoodImg(String goodOriginalImgName, String goodImgName, String goodImgUrl) {
        this.goodOriginalImgName = goodOriginalImgName;
        this.goodImgName = goodImgName;
        this.goodImgUrl = goodImgUrl;
    }
}