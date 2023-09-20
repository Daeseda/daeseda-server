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
    public Optional<ClothesDTO> getClothesById(Long id) {
        try {
            Optional<ClothesEntity> clothesEntity = clothesRepository.findById(id);
            if (clothesEntity.isPresent()) {
                ClothesDTO clothesDTO = convertToDTO(clothesEntity.get());
                return Optional.of(clothesDTO);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException("해당 의류가 없음", e);
        }
    }

    @Override
    public int createClothes(ClothesDTO clothesDTO) {
//        CategoryEntity categoryEntity = categoryRepository.findById(clothesDTO.getCategoryId())
//        ClothesEntity clothesEntity = ClothesEntity.builder()
//                .name(clothesDTO.getClothesName())
//                .category(categoryEntity)
//                .build();
        ClothesEntity clothesEntity = convertToEntity(clothesDTO);
        if(clothesRepository.save(clothesEntity) != null) {
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public int updateClothes(ClothesDTO clothesDTO) {
        ClothesEntity clothesEntity = convertToEntity(clothesDTO);
        if(clothesEntity != null) {
            clothesRepository.save(clothesEntity);
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteClothes(Long clothesId) {
        try {
            clothesRepository.deleteById(clothesId);
            return 1; // 삭제 성공 시 1 반환
        } catch (Exception e) {
            return 0; // 삭제 실패 시 0 반환
        }
    }
}
