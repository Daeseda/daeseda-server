package laundry.daeseda.service.review;

import laundry.daeseda.dto.review.ReviewDTO;
import laundry.daeseda.entity.review.ReviewEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<ReviewDTO> getAllReviews();
    Optional<ReviewDTO> getReviewById(Long reviewId);
    int createReview(ReviewDTO reviewDTO, MultipartFile image, Long orderId);
    int deleteReview(Long reviewId);
    ReviewDTO convertToDTO(ReviewEntity reviewEntity);
}
