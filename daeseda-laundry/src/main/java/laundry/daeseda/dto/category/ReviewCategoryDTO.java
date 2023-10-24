package laundry.daeseda.dto.category;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "리뷰 카테고리 ID", example = "자동 할당")
    private Long reviewCategoryId;

    @ApiModelProperty(value = "리뷰 ID", example = "1")
    private Long reviewId;

    @ApiModelProperty(value = "카테고리", example = "주문 ID를 통해 할당")
    private CategoryDTO categories;
}
