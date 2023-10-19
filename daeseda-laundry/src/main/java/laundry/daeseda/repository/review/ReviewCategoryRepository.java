package laundry.daeseda.repository.review;

import laundry.daeseda.entity.review.ReviewCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewCategoryRepository extends JpaRepository<ReviewCategoryEntity, Long> {
    List<ReviewCategoryEntity> findByReviewReviewId(Long reviewId);
}
