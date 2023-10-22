package laundry.daeseda.entity.payment;

import laundry.daeseda.entity.order.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cash")

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {

    @Id
    @Column(name = "cash_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cashId;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Column
    private String cardName;

    @Column
    private String cardNumber;

    @Column
    private Long paidAmount;

    @Column
    private String payMethod;

}
