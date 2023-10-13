package laundry.daeseda.entity.review;


import laundry.daeseda.entity.order.OrderEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@EnableJpaAuditing
@Table(name = "review")
public class ReviewEntity {
    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column
    private Long userId;

    @OneToOne
    @JoinColumn(name = "image_id")
    private ImageEntity image;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Column
    private Float rating;

    @Column
    private String reviewContent;

    @CreatedDate
    @Column(name = "regDate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "modDate")
    private LocalDateTime modDate;

}
