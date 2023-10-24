package laundry.daeseda.dto.review;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "리뷰 ID", example = "자동 할당")
    private Long reviewId;

    @ApiModelProperty(value = "유저 ID", example = "토큰으로 자동 할당")
    private Long userId;

    @ApiModelProperty(value = "유저 닉네임", example = "토크으로 자동 할당")
    private String userNickname;

    @ApiModelProperty(value = "이미지 URL", example = "리뷰 생성시 자동 할당")
    private String imageUrl;

    @ApiModelProperty(value = "주문 ID", example = "1")
    private Long orderId;

    @ApiModelProperty(value = "평점", example = "4.5")
    private Float rating;

    @ApiModelProperty(value = "리뷰 내용", example = "리뷰 내용")
    private String reviewContent;

    @ApiModelProperty(value = "생성 날짜", example = "자동 할당")
    private LocalDateTime regDate;
}
