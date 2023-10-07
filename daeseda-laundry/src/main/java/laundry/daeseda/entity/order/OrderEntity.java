package laundry.daeseda.entity.order;

import laundry.daeseda.constant.OrderStatus;
import laundry.daeseda.entity.BaseTimeEntity;
import laundry.daeseda.entity.clothes.ClothesEntity;
import laundry.daeseda.entity.user.AddressEntity;
import laundry.daeseda.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "orders")

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity extends BaseTimeEntity {

    @Id
    @Column(name = "order_id")
    @GeneratedValue
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @ManyToMany
    @JoinTable(
            name = "orders_clothes",
            joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "clothes_id", referencedColumnName = "clothes_id")})
    private Set<ClothesEntity> clothes;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalPrice;

    private String washingMethod;

    private LocalDate pickupDate;

    private LocalDate deliveryDate;

    private String deliveryLocation;

}
