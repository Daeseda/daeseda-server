package laundry.daeseda.entity.category;

import laundry.daeseda.entity.review.ReviewCategoryEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "category")
public class CategoryEntity {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<ReviewCategoryEntity> reviewCategories;

}
