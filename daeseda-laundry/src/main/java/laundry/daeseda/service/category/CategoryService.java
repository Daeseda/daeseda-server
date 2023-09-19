package laundry.daeseda.service.category;


import laundry.daeseda.dto.category.CategoryDTO;
import laundry.daeseda.entity.category.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    Optional<CategoryDTO> getCategoryById(Long categoryId);
    void createCategory(CategoryDTO categoryDTO);
    void updateCategory(Long categoryId, CategoryDTO categoryDTO);
    void deleteCategory(Long categoryId);


    default CategoryEntity convertToEntity(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(categoryDTO.getCategoryId())
                .name(categoryDTO.getCategoryName())
                .build();
        return categoryEntity;
    }
    default CategoryDTO convertToDTO(CategoryEntity categoryEntity) {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .categoryId(categoryEntity.getId())
                .categoryName(categoryEntity.getName())
                .build();
        return categoryDTO;
    }
}
