package ShopJutta.entity;

import ShopJutta.constant.SelectStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "selects")
@Getter
@Setter
public class Select extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "select_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "join_id")
    private Join join;

    private LocalDateTime selectDate;   // 상품 선택 날짜

    @Enumerated(EnumType.STRING)
    private SelectStatus selectStatus;  // 상품 선택 상태

    @OneToMany(mappedBy = "select", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SelectItem> selectItems = new ArrayList<>();

    public void addSelectItem(SelectItem selectItem) {
        selectItems.add(selectItem);
        selectItem.setSelect(this);
    }

    public static Select createSelect(Join join, List<SelectItem> selectItemList) {
        Select select = new Select();
        select.setJoin(join);

        for (SelectItem selectItem : selectItemList) {
            select.addSelectItem(selectItem);
        }

        select.setSelectStatus(SelectStatus.ORDER); // 상품 주문 상태
        select.setSelectDate(LocalDateTime.now());  // 상품 주문 시간
        return select;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for(SelectItem selectItem : selectItems) {
            totalPrice += selectItem.getTotalPrice();
        }
        return totalPrice;
    }

    public void cancelOrder() {
        this.selectStatus = SelectStatus.CANCEL;
        for(SelectItem selectItem : selectItems) {
            selectItem.cancel();
        }
    }
}