package laundry.daeseda.dto.review;

import laundry.daeseda.entity.review.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReviewDTO {
    private Long reviewId;
    private Long userId;
    private ImageEntity image;
    private Long orderId;
    private Float rating;
    private String reviewContent;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
