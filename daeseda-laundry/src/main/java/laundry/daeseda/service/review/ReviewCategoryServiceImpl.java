package laundry.daeseda.service.review;

import laundry.daeseda.dto.category.CategoryDTO;
import laundry.daeseda.dto.category.ReviewCategoryDTO;
import laundry.daeseda.dto.review.ReviewDTO;
import laundry.daeseda.entity.review.ReviewCategoryEntity;
import laundry.daeseda.repository.review.ReviewCategoryRepository;
import laundry.daeseda.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewCategoryServiceImpl implements ReviewCategoryService{

    private final ReviewCategoryRepository reviewCategoryRepository;
    private final CategoryService categoryService;

    @Override
    public List<ReviewCategoryDTO> getAllCategoriesByReview(ReviewDTO reviewDTO) {
        List<ReviewCategoryEntity> reviewCategoryEntities = reviewCategoryRepository.findAll();
        List<ReviewCategoryDTO> reviewCategoryDTOList = new ArrayList<>();
        for (ReviewCategoryEntity reviewCategory : reviewCategoryEntities) {
            if (reviewCategory.getReview().getReviewId().equals(reviewDTO.getReviewId())) {
                CategoryDTO categoryDTO = categoryService.convertToDTO(reviewCategory.getCategory());
                ReviewCategoryDTO reviewCategoryDTO = ReviewCategoryDTO.builder()
                        .categories(categoryDTO)
                        .build();
                reviewCategoryDTOList.add(reviewCategoryDTO);
            }
        }
        return reviewCategoryDTOList;
    }

    @Override
    public ReviewCategoryDTO convertToDTO(ReviewCategoryEntity reviewCategory) {
        ReviewCategoryDTO reviewCategoryDTO = ReviewCategoryDTO.builder()
                .categories(categoryService.convertToDTO(reviewCategory.getCategory()))
                .build();
        return reviewCategoryDTO;
    }

}
