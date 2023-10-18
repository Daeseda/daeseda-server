package laundry.daeseda.service.review;

import laundry.daeseda.dto.category.CategoryDTO;
import laundry.daeseda.dto.reply.ReplyDTO;
import laundry.daeseda.dto.review.ReviewDTO;
import laundry.daeseda.entity.category.CategoryEntity;
import laundry.daeseda.entity.review.ReviewEntity;
import laundry.daeseda.service.category.CategoryService;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<ReviewDTO> getAllReviews();
    Optional<ReviewDTO> getReviewById(Long reviewId);
    int createReview(ReviewDTO reviewDTO, MultipartFile image, Long orderId);

//    int updateReview(ReviewDTO reviewDTO, MultipartFile image);
    int deleteReview(Long reviewId);
    ReviewDTO convertToDTO(ReviewEntity reviewEntity);
}
