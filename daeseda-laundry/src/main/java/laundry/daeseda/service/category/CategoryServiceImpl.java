package laundry.daeseda.service.category;


import laundry.daeseda.dto.category.CategoryDTO;
import laundry.daeseda.entity.category.CategoryEntity;
import laundry.daeseda.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

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
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(categoryId);
        if (categoryEntity.isPresent()) {
            CategoryDTO categoryDTO = convertToDTO(categoryEntity.get());
            return Optional.of(categoryDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void createCategory(CategoryDTO categoryDTO) {
//        CategoryEntity categoryEntity = CategoryEntity.builder()
//                .name(categoryDTO.getCategoryName())
//                .build();
        CategoryEntity categoryEntity = convertToEntity(categoryDTO);
        categoryRepository.save(categoryEntity);
    }

    @Override
    public void updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(categoryId);
        if(categoryEntity.isPresent()) {
            CategoryEntity category = categoryEntity.get();

            category = CategoryEntity.builder()
                    .id(category.getId())
                    .name(categoryDTO.getCategoryName())
                    .build();

            categoryRepository.save(category);
        }
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
