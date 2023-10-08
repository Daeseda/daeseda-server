package laundry.daeseda.entity.order;

import laundry.daeseda.entity.clothes.ClothesEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "clothes_count")

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClothesCountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "clothes_id")
    private ClothesEntity clothes;

    private int count;
}
