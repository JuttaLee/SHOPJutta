package ShopJutta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "basket_item")
public class BasketItem extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "basket_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_id")
    private Good good;

    private int count;

    public static BasketItem createBasketItem(Basket basket, Good good, int count) {
        BasketItem basketItem = new BasketItem();
        basketItem.setBasket(basket);
        basketItem.setGood(good);
        basketItem.setCount(count);
        return basketItem;
    }

    public void addBasket(int count) {
        this.count += count;
    }

    public void updateCount(int count) {
        this.count = count;
    }
}