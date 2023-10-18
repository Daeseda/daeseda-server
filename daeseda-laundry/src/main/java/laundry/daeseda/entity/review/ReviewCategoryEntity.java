package laundry.daeseda.entity.review;

import laundry.daeseda.entity.category.CategoryEntity;
import lombok.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@EnableJpaAuditing
@Table(name = "review_category")
public class ReviewCategoryEntity {
    @Id
    @Column(name = "review_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private ReviewEntity review;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
}
