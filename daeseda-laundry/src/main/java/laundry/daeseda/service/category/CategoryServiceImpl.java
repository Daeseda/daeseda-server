package laundry.daeseda.service.category;


import laundry.daeseda.dto.category.CategoryDTO;
import laundry.daeseda.dto.category.ReviewCategoryDTO;
import laundry.daeseda.entity.category.CategoryEntity;
import laundry.daeseda.entity.clothes.ClothesEntity;
import laundry.daeseda.entity.order.ClothesCountEntity;
import laundry.daeseda.entity.order.OrderEntity;
import laundry.daeseda.repository.category.CategoryRepository;
import laundry.daeseda.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<CategoryEntity> categoryList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for(CategoryEntity categoryEntity : categoryList) {
            CategoryDTO categoryDTO = convertToDTO(categoryEntity);
            categoryDTOList.add(categoryDTO);
        }
        return categoryDTOList;
    }

    @Override
    public Optional<CategoryDTO> getCategoryById(Long categoryId) {
        try {
            Optional<CategoryEntity> categoryEntity = categoryRepository.findById(categoryId);
            if (categoryEntity.isPresent()) {
                CategoryDTO categoryDTO = convertToDTO(categoryEntity.get());
                return Optional.of(categoryDTO);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            // 데이터베이스 조회 중 예외 발생 시 처리
            throw new RuntimeException("해당 카테고리가 없음", e);
        }
    }

    @Override
    public int createCategory(CategoryDTO categoryDTO) {
//        CategoryEntity categoryEntity = CategoryEntity.builder()
//                .name(categoryDTO.getCategoryName())
//                .build();
        CategoryEntity categoryEntity = convertToEntity(categoryDTO);
        if(categoryRepository.save(categoryEntity) != null) {
            return 1;
        }
        else {
            return 0;
        }

    }

    @Override
    public int updateCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = convertToEntity(categoryDTO);
        if(categoryEntity != null) {
            categoryRepository.save(categoryEntity);
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public int deleteCategory(Long categoryId) {
        try {
            categoryRepository.deleteById(categoryId);
            return 1; // 삭제 성공 시 1 반환
        } catch (Exception e) {
            return 0; // 삭제 실패 시 0 반환
        }
    }

    @Override
    public List<ReviewCategoryDTO> getCategoriesByOrderId(OrderEntity orderEntity) {
        List<ReviewCategoryDTO> reviewCategoryDTOList = new ArrayList<>();

        List<ClothesCountEntity> clothesCounts = orderEntity.getClothesCounts();

        for (ClothesCountEntity clothesCount : clothesCounts) {
            ClothesEntity clothes = clothesCount.getClothes();

            if (clothes != null) {
                CategoryEntity category = clothes.getCategory();

                if (category != null) {
                    ReviewCategoryDTO reviewCategoryDTO = ReviewCategoryDTO.builder()
                            .categories(convertToDTO(category))
                            .build();

                    if (!reviewCategoryDTOList.contains(reviewCategoryDTO)) {
                        reviewCategoryDTOList.add(reviewCategoryDTO);
                    }
                }
            }
        }
        return reviewCategoryDTOList;
    }

}
