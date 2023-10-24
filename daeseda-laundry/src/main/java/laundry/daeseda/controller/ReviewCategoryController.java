package laundry.daeseda.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import laundry.daeseda.dto.category.ReviewCategoryDTO;
import laundry.daeseda.dto.review.ReviewDTO;
import laundry.daeseda.service.review.ReviewCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"리뷰 카테고리 API 정보를 제공하는 Controller"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/review-category")
public class ReviewCategoryController {
    private final ReviewCategoryService reviewCategoryService;


    @ApiOperation(value = "특정 리뷰에서 주문내역에 있는 카테고리를 보는 메서드")
    @GetMapping("/{reviewId}")
    public ResponseEntity<List<ReviewCategoryDTO>> getCategoryByReview(ReviewDTO reviewDTO) {
        List<ReviewCategoryDTO> reviewCategory = reviewCategoryService.getAllCategoriesByReview(reviewDTO);
        return ResponseEntity.ok().body(reviewCategory);
    }
}
