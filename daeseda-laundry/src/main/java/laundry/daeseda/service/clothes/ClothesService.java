package laundry.daeseda.service.clothes;

import laundry.daeseda.dto.clothes.ClothesDTO;
import laundry.daeseda.entity.category.CategoryEntity;
import laundry.daeseda.entity.clothes.ClothesEntity;

import java.util.List;
import java.util.Optional;

public interface ClothesService {

    List<ClothesDTO> getAllClothes();

    Optional<ClothesDTO> getClothesById(Long clothesId);

    int createClothes(ClothesDTO clothesDTO);

    int updateClothes(ClothesDTO clothesDTO);

    int deleteClothes(Long clothesId);

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
