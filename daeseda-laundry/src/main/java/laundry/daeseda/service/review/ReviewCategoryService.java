package laundry.daeseda.service.review;

import laundry.daeseda.dto.category.ReviewCategoryDTO;
import laundry.daeseda.dto.review.ImageDTO;
import laundry.daeseda.dto.review.ReviewDTO;
import laundry.daeseda.entity.review.ImageEntity;
import laundry.daeseda.entity.review.ReviewCategoryEntity;

import java.util.List;

public interface ReviewCategoryService {
    List<ReviewCategoryDTO> getAllCategoriesByReview(ReviewDTO reviewDTO);

    int deleteReviewCategoriesByReviewId(Long reviewId);
    ReviewCategoryDTO convertToDTO(ReviewCategoryEntity reviewCategory);
}
