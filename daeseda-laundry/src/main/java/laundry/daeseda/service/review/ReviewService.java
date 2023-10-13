package laundry.daeseda.service.review;

import laundry.daeseda.dto.reply.ReplyDTO;
import laundry.daeseda.dto.review.ReviewDTO;
import laundry.daeseda.entity.review.ReviewEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<ReviewDTO> getAllReviews();
    Optional<ReviewDTO> getReviewById(Long reviewId);
    int createReview(ReviewDTO reviewDTO);
    int updateReview(ReviewDTO reviewDTO);
    int deleteReview(Long reviewId);

    default ReviewDTO convertToDTO(ReviewEntity reviewEntity) {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .reviewId(reviewEntity.getReviewId())
                .image(reviewEntity.getImage())
                .orderId(reviewEntity.getOrder().getOrderId())
                .rating(reviewEntity.getRating())
                .reviewContent(reviewEntity.getReviewContent())
                .regDate(LocalDateTime.now())
                .modDate(LocalDateTime.now())
                .build();
        return reviewDTO;
    }
}
