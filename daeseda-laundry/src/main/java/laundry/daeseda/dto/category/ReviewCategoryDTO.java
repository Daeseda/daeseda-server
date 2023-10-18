package laundry.daeseda.dto.category;

import laundry.daeseda.entity.review.ReviewCategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReviewCategoryDTO {
    private CategoryDTO categories;
}
