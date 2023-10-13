package laundry.daeseda.repository.review;

import laundry.daeseda.entity.review.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
}
