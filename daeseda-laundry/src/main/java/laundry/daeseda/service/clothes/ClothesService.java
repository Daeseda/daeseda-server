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
                .categoryId(clothesDTO.getCategoryId())
                .build();
        ClothesEntity clothesEntity = ClothesEntity.builder()
                .clothesId(clothesDTO.getClothesId())
                .clothesName(clothesDTO.getClothesName())
                .clothesPrice(clothesDTO.getClothesPrice())
                .category(categoryEntity)
                .build();
        return clothesEntity;
    }

    default ClothesDTO convertToDTO(ClothesEntity clothesEntity) {
        return ClothesDTO.builder()
                .clothesId(clothesEntity.getClothesId())
                .clothesName(clothesEntity.getClothesName())
                .clothesPrice(clothesEntity.getClothesPrice())
                .categoryId(clothesEntity.getCategory().getCategoryId())
                .build();
    }
}
