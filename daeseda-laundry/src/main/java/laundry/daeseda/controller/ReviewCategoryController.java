package laundry.daeseda.controller;

import laundry.daeseda.dto.category.ReviewCategoryDTO;
import laundry.daeseda.dto.review.ReviewDTO;
import laundry.daeseda.service.review.ReviewCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/review-category")
public class ReviewCategoryController {
    private final ReviewCategoryService reviewCategoryService;

    @GetMapping("/{reviewId}")
    public ResponseEntity<List<ReviewCategoryDTO>> getCategoryByReview(ReviewDTO reviewDTO) {
        List<ReviewCategoryDTO> reviewCategory = reviewCategoryService.getAllCategoriesByReview(reviewDTO);
        return ResponseEntity.ok().body(reviewCategory);
    }
}
