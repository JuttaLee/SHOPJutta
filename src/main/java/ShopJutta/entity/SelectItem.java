package ShopJutta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SelectItem extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "select_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_id")
    private Good good;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "select_id")
    private Select select;

    private int SelectPrice;    // 선택한 상품 가격
    private int SelectCount;    // 선택한 상품 수량

    public static SelectItem createSelectItem(Good good, int selectCount) {
        SelectItem selectItem = new SelectItem();
        selectItem.setGood(good);
        selectItem.setSelectCount(selectCount);
        selectItem.setSelectPrice(good.getGoodPrice());
        good.removeStock(selectCount);
        return selectItem;
    }

    public int getTotalPrice() {
        return getSelectPrice() * getSelectCount();
    }

    public void cancel() {

    }
}