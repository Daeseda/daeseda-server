package laundry.daeseda.service.clothes;

import laundry.daeseda.dto.category.CategoryDTO;
import laundry.daeseda.dto.clothes.ClothesDTO;
import laundry.daeseda.entity.category.CategoryEntity;
import laundry.daeseda.entity.clothes.ClothesEntity;
import laundry.daeseda.repository.category.CategoryRepository;
import laundry.daeseda.repository.clothes.ClothesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClothesServiceImpl implements ClothesService {
    private final ClothesRepository clothesRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public void createClothes(ClothesDTO clothesDTO) {
//        CategoryEntity categoryEntity = categoryRepository.findById(clothesDTO.getCategoryId())
//        ClothesEntity clothesEntity = ClothesEntity.builder()
//                .name(clothesDTO.getClothesName())
//                .category(categoryEntity)
//                .build();
        ClothesEntity clothesEntity = convertToEntity(clothesDTO);
        clothesRepository.save(clothesEntity);
    }

    @Override
    public Optional<ClothesDTO> getClothesById(Long id) {
        Optional<ClothesEntity> clothesEntity = clothesRepository.findById(id);
        if (clothesEntity.isPresent()) {
            ClothesDTO clothesDTO = convertToDTO(clothesEntity.get());
            return Optional.of(clothesDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<ClothesDTO> getAllClothes() {
        List<ClothesEntity> clothesList = clothesRepository.findAll();
        List<ClothesDTO> clothesDTOList = new ArrayList<>();
        for(ClothesEntity clothesEntity : clothesList) {
            ClothesDTO clothesDTO = convertToDTO(clothesEntity);
            clothesDTOList.add(clothesDTO);
        }
        return clothesDTOList;
    }

    @Override
    public void deleteClothes(Long clothesId) {
        clothesRepository.deleteById(clothesId);
    }

    @Override
    public void updateClothes(Long clothesId, ClothesDTO clothesDTO) {
        Optional<ClothesEntity> clothesEntity = clothesRepository.findById(clothesId);
        if(clothesEntity.isPresent()) {
            ClothesEntity clothes = clothesEntity.get();
            clothes = ClothesEntity.builder()
                    .id(clothes.getId())
                    .name(clothesDTO.getClothesName())
                    .category(clothes.getCategory())
                    .build();

            clothesRepository.save(clothes);
        }
    }
}
