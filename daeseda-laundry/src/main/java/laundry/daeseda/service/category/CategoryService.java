package laundry.daeseda.service.category;


import laundry.daeseda.dto.category.CategoryDTO;
import laundry.daeseda.dto.category.ReviewCategoryDTO;
import laundry.daeseda.entity.category.CategoryEntity;
import laundry.daeseda.entity.order.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    Optional<CategoryDTO> getCategoryById(Long categoryId);
    int createCategory(CategoryDTO categoryDTO);
    int updateCategory(CategoryDTO categoryDTO);
    int deleteCategory(Long categoryId);
    List<ReviewCategoryDTO> getCategoriesByOrderId(OrderEntity order);


    default CategoryEntity convertToEntity(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryId(categoryDTO.getCategoryId())
                .categoryName(categoryDTO.getCategoryName())
                .build();
        return categoryEntity;
    }
    default CategoryDTO convertToDTO(CategoryEntity categoryEntity) {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .categoryId(categoryEntity.getCategoryId())
                .categoryName(categoryEntity.getCategoryName())
                .build();
        return categoryDTO;
    }
}
