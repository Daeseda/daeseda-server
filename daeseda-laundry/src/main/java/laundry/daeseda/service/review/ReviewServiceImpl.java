package laundry.daeseda.service.review;

import laundry.daeseda.dto.category.CategoryDTO;
import laundry.daeseda.dto.category.ReviewCategoryDTO;
import laundry.daeseda.dto.clothes.ClothesCountDto;
import laundry.daeseda.dto.review.ReviewDTO;
import laundry.daeseda.entity.category.CategoryEntity;
import laundry.daeseda.entity.clothes.ClothesEntity;
import laundry.daeseda.entity.order.ClothesCountEntity;
import laundry.daeseda.entity.order.OrderEntity;
import laundry.daeseda.entity.review.ReviewCategoryEntity;
import laundry.daeseda.entity.review.ReviewEntity;
import laundry.daeseda.entity.user.UserEntity;
import laundry.daeseda.repository.category.CategoryRepository;
import laundry.daeseda.repository.order.OrderRepository;
import laundry.daeseda.repository.review.ReviewCategoryRepository;
import laundry.daeseda.repository.review.ReviewRepository;
import laundry.daeseda.repository.user.UserRepository;
import laundry.daeseda.service.category.CategoryService;
import laundry.daeseda.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewCategoryRepository reviewCategoryRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final OrderRepository orderRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final ReviewCategoryService reviewCategoryService;
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
            if (order.getOrderId().equals(orderId)) {
                matchOrder = order;
                break;
            }
        }

        if(matchOrder != null) {
            String imageUrl = imageService.saveImage(image);

            ReviewEntity reviewEntity = ReviewEntity.builder()
                    .reviewId(reviewDTO.getReviewId())
                    .user(userEntity)
                    .userNickname(userEntity.getUserNickname())
                    .imageUrl(imageUrl)
                    .order(matchOrder)
                    .rating(reviewDTO.getRating())
                    .reviewContent(reviewDTO.getReviewContent())
                    .regDate(LocalDateTime.now())
                    .modDate(LocalDateTime.now())
                    .build();
            reviewEntity = reviewRepository.save(reviewEntity);


            List<ReviewCategoryDTO> reviewCategoryDTOList = categoryService.getCategoriesByOrderId(matchOrder);
            if(reviewCategoryDTOList != null) {
                for(ReviewCategoryDTO reviewCategoryDTO : reviewCategoryDTOList) {
                    CategoryEntity categoryEntity = categoryRepository.findById(reviewCategoryDTO.getCategories().getCategoryId()).orElse(null);

                    ReviewCategoryEntity reviewCategory = ReviewCategoryEntity.builder()
                            .category(categoryEntity)
                            .review(reviewEntity)
                            .build();
                    reviewCategoryRepository.save(reviewCategory);
                }
            }
            return 1;
        }
        return 0;
    }

//    @Override
//    public int updateReview(ReviewDTO reviewDTO, MultipartFile image) {
//        UserEntity userEntity = userRepository.findByUserEmail(SecurityUtil.getCurrentUsername().get())
//                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
//
//        ReviewEntity review = reviewRepository.findById(reviewDTO.getReviewId()).orElseThrow(null);
//
//        if(review != null && review.getUser().getUserId().equals(userEntity.getUserId())) {
//            String imageUrl = review.getImageUrl();
//
//            if (image != null && !image.isEmpty()) {
//                imageUrl = imageService.saveImage(image);
//            }
//
//            OrderEntity orderEntity = review.getOrder();
//
//            ReviewEntity reviewEntity = ReviewEntity.builder()
//                    .reviewId(reviewDTO.getReviewId())
//                    .user(userEntity)
//                    .imageUrl(imageUrl)
//                    .order(orderEntity)
//                    .rating(reviewDTO.getRating())
//                    .reviewTitle(reviewDTO.getReviewTitle())
//                    .reviewContent(reviewDTO.getReviewContent())
//                    .regDate(LocalDateTime.now())
//                    .modDate(LocalDateTime.now())
//                    .build();
//            reviewRepository.save(reviewEntity); // 게시글 저장 및 반환
//            return 1;
//        }
//        return 0;
//    }

    @Override
    public int deleteReview(Long reviewId) {
        UserEntity userEntity = userRepository.findByUserEmail(SecurityUtil.getCurrentUsername().get())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        Optional<ReviewEntity> review = reviewRepository.findById(reviewId);

        if(review.isPresent()) {
            ReviewEntity reviewEntity = review.get();

            if(userEntity.getUserId().equals(reviewEntity.getUser().getUserId())) {
                try {
                    reviewRepository.deleteById(reviewId);
                    reviewCategoryService.deleteReviewCategoriesByReviewId(reviewId);
                    return 1;
                } catch (Exception e) {
                    return 0;
                }
            } else {
                throw new AccessDeniedException("리뷰 삭제 권한이 없습니다.");
            }
        } else {
            throw new EntityNotFoundException("리뷰를 찾을 수 없습니다.");
        }
    }

    @Override
    public ReviewDTO convertToDTO(ReviewEntity reviewEntity) {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .reviewId(reviewEntity.getReviewId())
                .userId(reviewEntity.getUser().getUserId())
                .userNickname(reviewEntity.getUser().getUserNickname())
                .imageUrl(reviewEntity.getImageUrl())
                .orderId(reviewEntity.getOrder().getOrderId())
                .rating(reviewEntity.getRating())
                .reviewContent(reviewEntity.getReviewContent())
                .regDate(LocalDateTime.now())
                .build();
        return reviewDTO;
    }


    public List<CategoryEntity> convertToEntities(List<CategoryDTO> categoryDTOS) {
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        for (CategoryDTO categoryDTO : categoryDTOS) {
            CategoryEntity categoryEntity = categoryService.convertToEntity(categoryDTO);
            categoryEntities.add(categoryEntity);
        }
        return categoryEntities;
    }

}
