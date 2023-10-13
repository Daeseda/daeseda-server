package laundry.daeseda.service.review;

import laundry.daeseda.dto.review.ReviewDTO;
import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.entity.board.BoardEntity;
import laundry.daeseda.entity.review.ReviewEntity;
import laundry.daeseda.entity.user.UserEntity;
import laundry.daeseda.repository.review.ReviewRepository;
import laundry.daeseda.service.user.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final CustomUserDetailsService customUserDetailsService;
    private final ReviewRepository reviewRepository;
    @Override
    public List<ReviewDTO> getAllReviews() {
        return null;
    }

    @Override
    public Optional<ReviewDTO> getReviewById(Long reviewId) {
        return Optional.empty();
    }

    @Override
    public int createReview(ReviewDTO reviewDTO) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.isAuthenticated()) {
//            UserDto user = (UserDto) customUserDetailsService.loadUserByUsername(authentication.getName());
//
//            ReviewEntity reviewEntity = ReviewEntity.builder()
//                    .reviewId(reviewDTO.getReviewId())
//                    .userId(userId)
//                    .image()
//                    .order(reviewDTO.getOrderId())
//                    .reviewContent(reviewDTO.getReviewContent())
//                    .regDate(LocalDateTime.now())
//                    .modDate(LocalDateTime.now())
//                    .build();
//            reviewRepository.save(reviewEntity); // 게시글 저장 및 반환
//            if (boardRepository.save(boardEntity) != null) {
//                return 1;
//            }
//        } else {
//            throw new BadCredentialsException("로그인이 필요합니다.");
//        }
        return 1;
    }

    @Override
    public int updateReview(ReviewDTO reviewDTO) {
        return 0;
    }

    @Override
    public int deleteReview(Long reviewId) {
        return 0;
    }
}
