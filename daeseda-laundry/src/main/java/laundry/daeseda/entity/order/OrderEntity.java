package laundry.daeseda.entity.order;

import laundry.daeseda.constant.OrderStatus;
import laundry.daeseda.entity.BaseTimeEntity;
import laundry.daeseda.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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

    @Column(updatable = false)
    private String address;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Column
    private List<ClothesCountEntity> clothesCounts;

    @Enumerated(EnumType.STRING)
    @Column
    private OrderStatus orderStatus;

    @Column
    private Long totalPrice;

    @Column
    private String washingMethod;

    @Column
    private LocalDate pickupDate;

    @Column
    private LocalDate deliveryDate;

    @Column
    private String deliveryLocation;

}
