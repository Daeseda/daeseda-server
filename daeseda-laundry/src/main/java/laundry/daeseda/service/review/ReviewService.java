package laundry.daeseda.service.review;

import laundry.daeseda.dto.reply.ReplyDTO;
import laundry.daeseda.dto.review.ReviewDTO;
import laundry.daeseda.entity.review.ReviewEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<ReviewDTO> getAllReviews();
    Optional<ReviewDTO> getReviewById(Long reviewId);
    int createReview(ReviewDTO reviewDTO, MultipartFile image, Long orderId);
    int updateReview(ReviewDTO reviewDTO, MultipartFile image);
    int deleteReview(Long reviewId);

    default ReviewDTO convertToDTO(ReviewEntity reviewEntity) {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .reviewId(reviewEntity.getReviewId())
                .userId(reviewEntity.getUser().getUserId())
                .imageUrl(reviewEntity.getImageUrl())
                .orderId(reviewEntity.getOrder().getOrderId())
                .rating(reviewEntity.getRating())
                .reviewTitle(reviewEntity.getReviewTitle())
                .reviewContent(reviewEntity.getReviewContent())
                .regDate(LocalDateTime.now())
                .modDate(LocalDateTime.now())
                .build();
        return reviewDTO;
    }


}
