package laundry.daeseda.controller;

import laundry.daeseda.dto.category.ReviewCategoryDTO;
import laundry.daeseda.dto.reply.ReplyDTO;
import laundry.daeseda.dto.review.ImageDTO;
import laundry.daeseda.dto.review.ReviewDTO;
import laundry.daeseda.service.review.ReviewCategoryService;
import laundry.daeseda.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/list")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> review = reviewService.getAllReviews();
        return ResponseEntity.ok().body(review);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable Long reviewId) {
        ReviewDTO review = reviewService.getReviewById(reviewId).orElse(null);
        if(review != null) {
            return ResponseEntity.ok(review);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerReview(@RequestParam("image") MultipartFile image, ReviewDTO reviewDTO, Long orderId) {
        reviewService.createReview(reviewDTO, image, orderId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @PutMapping("/{reviewId}")
//    public ResponseEntity<String> updateReview(@RequestParam("image") MultipartFile image, ReviewDTO reviewDTO) {
//        if(reviewService.updateReview(reviewDTO, image) > 0) {
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("업데이트가 완료되었습니다.");
//        }
//        else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("업데이트에 실패하였습니다.");
//        }
//    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        if(reviewService.deleteReview(reviewId) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("삭제가 성공적으로 실행되었습니다.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제에 실패했습니다.");
        }
    }
}
