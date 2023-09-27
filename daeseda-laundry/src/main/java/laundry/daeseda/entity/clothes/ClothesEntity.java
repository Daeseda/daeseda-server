package laundry.daeseda.entity.clothes;

import laundry.daeseda.entity.category.CategoryEntity;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "clothes")
public class ClothesEntity {
    @Id
    @Column(name = "clothes_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clothesId;

    @Column
    private String clothesName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
}