package laundry.daeseda.service.review;

import laundry.daeseda.dto.reply.ReplyDTO;
import laundry.daeseda.dto.review.ImageDTO;
import laundry.daeseda.dto.review.ReviewDTO;
import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.entity.board.BoardEntity;
import laundry.daeseda.entity.order.OrderEntity;
import laundry.daeseda.entity.reply.ReplyEntity;
import laundry.daeseda.entity.review.ImageEntity;
import laundry.daeseda.entity.review.ReviewEntity;
import laundry.daeseda.entity.user.UserEntity;
import laundry.daeseda.repository.order.OrderRepository;
import laundry.daeseda.repository.review.ReviewRepository;
import laundry.daeseda.repository.user.UserRepository;
import laundry.daeseda.service.order.OrderService;
import laundry.daeseda.service.user.CustomUserDetailsService;
import laundry.daeseda.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    @Value("${custom.image-directory}")
    private String imageDir;

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final OrderRepository orderRepository;
    @Override
    public List<ReviewDTO> getAllReviews() {
        List<ReviewEntity> reviewList = reviewRepository.findAll();
        List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for(ReviewEntity reviewEntity : reviewList) {
            ReviewDTO reviewDTO = convertToDTO(reviewEntity);
            reviewDTOList.add(reviewDTO);
        }
        return reviewDTOList;
    }

    @Override
    public Optional<ReviewDTO> getReviewById(Long reviewId) {
        try {
            Optional<ReviewEntity> reviewEntity = reviewRepository.findById(reviewId);
            if (reviewEntity.isPresent()) {
                ReviewDTO reviewDTO = convertToDTO(reviewEntity.get());
                return Optional.of(reviewDTO);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            // 데이터베이스 조회 중 예외 발생 시 처리
            throw new RuntimeException("해당 리뷰가 없음", e);
        }
    }

    @Override
    public int createReview(ReviewDTO reviewDTO, MultipartFile image, Long orderId) {
        UserEntity userEntity = userRepository.findByUserEmail(SecurityUtil.getCurrentUsername().get())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        List<OrderEntity> userOrders = orderRepository.getByUser(userEntity);
        OrderEntity matchOrder = null;
        for(OrderEntity order : userOrders) {
            if(order.getOrderId().equals(orderId)) {
                matchOrder = order;
                break;
            }
        }
        if(matchOrder != null) {
            String imageUrl = imageService.saveImage(image);

            ReviewEntity reviewEntity = ReviewEntity.builder()
                    .reviewId(reviewDTO.getReviewId())
                    .user(userEntity)
                    .imageUrl(imageUrl)
                    .order(matchOrder)
                    .rating(reviewDTO.getRating())
                    .reviewTitle(reviewDTO.getReviewTitle())
                    .reviewContent(reviewDTO.getReviewContent())
                    .regDate(LocalDateTime.now())
                    .modDate(LocalDateTime.now())
                    .build();
            reviewRepository.save(reviewEntity); // 게시글 저장 및 반환
            return 1;
        }
        return 0;
    }

    @Override
    public int updateReview(ReviewDTO reviewDTO, MultipartFile image) {
        UserEntity userEntity = userRepository.findByUserEmail(SecurityUtil.getCurrentUsername().get())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        ReviewEntity review = reviewRepository.findById(reviewDTO.getReviewId()).orElseThrow(null);

        if(review != null && review.getUser().getUserId().equals(userEntity.getUserId())) {
            String imageUrl = review.getImageUrl();

            if (image != null && !image.isEmpty()) {
                imageUrl = imageService.saveImage(image);
            }

            OrderEntity orderEntity = review.getOrder();

            ReviewEntity reviewEntity = ReviewEntity.builder()
                    .reviewId(reviewDTO.getReviewId())
                    .user(userEntity)
                    .imageUrl(imageUrl)
                    .order(orderEntity)
                    .rating(reviewDTO.getRating())
                    .reviewTitle(reviewDTO.getReviewTitle())
                    .reviewContent(reviewDTO.getReviewContent())
                    .regDate(LocalDateTime.now())
                    .modDate(LocalDateTime.now())
                    .build();
            reviewRepository.save(reviewEntity); // 게시글 저장 및 반환
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteReview(Long reviewId) {
        try {
            reviewRepository.deleteById(reviewId);
            return 1; // 삭제 성공 시 1 반환
        } catch (Exception e) {
            return 0; // 삭제 실패 시 0 반환
        }
    }
}
