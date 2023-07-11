package ShopJutta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "basket")
@Getter
@Setter
@ToString
public class Basket extends BaseEntity {
    @Id
    @Column(name = "basket_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "join_id")
    private Join join;

    public static Basket createBasket(Join join) {
        Basket basket = new Basket();
        basket.setJoin(join);
        return basket;
    }
}