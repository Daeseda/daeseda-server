package laundry.daeseda.service.clothes;

import laundry.daeseda.dto.clothes.ClothesDTO;
import laundry.daeseda.entity.category.CategoryEntity;
import laundry.daeseda.entity.clothes.ClothesEntity;

import java.util.List;
import java.util.Optional;

public interface ClothesService {
    void createClothes(ClothesDTO clothesDTO);

    Optional<ClothesDTO> getClothesById(Long clothesId);

    List<ClothesDTO> getAllClothes();

    void deleteClothes(Long clothesId);

    void updateClothes(Long clothesId, ClothesDTO clothesDTO);

    default ClothesEntity convertToEntity(ClothesDTO clothesDTO) {
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(clothesDTO.getCategoryId())
                .build();
        ClothesEntity clothesEntity = ClothesEntity.builder()
                .id(clothesDTO.getClothesId())
                .name(clothesDTO.getClothesName())
                .category(categoryEntity)
                .build();
        return clothesEntity;
    }

    default ClothesDTO convertToDTO(ClothesEntity clothesEntity) {
        return ClothesDTO.builder()
                .clothesId(clothesEntity.getId())
                .clothesName(clothesEntity.getName())
                .categoryId(clothesEntity.getCategory().getId())
                .build();
    }
}
