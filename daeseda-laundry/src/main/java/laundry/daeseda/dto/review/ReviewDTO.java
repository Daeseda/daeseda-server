package laundry.daeseda.dto.review;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import laundry.daeseda.dto.category.CategoryDTO;
import laundry.daeseda.dto.category.ReviewCategoryDTO;
import laundry.daeseda.entity.category.CategoryEntity;
import laundry.daeseda.entity.review.ImageEntity;
import laundry.daeseda.entity.review.ReviewCategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReviewDTO {
    private Long reviewId;
    private Long userId;
    private String imageUrl;
    private List<ReviewCategoryDTO> categories;
    private Long orderId;
    private Float rating;
    private String reviewTitle;
    private String reviewContent;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
