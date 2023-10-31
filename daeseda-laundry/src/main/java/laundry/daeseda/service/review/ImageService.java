package laundry.daeseda.service.review;

import laundry.daeseda.dto.review.ImageDTO;
import laundry.daeseda.entity.review.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImageService {

    String saveImage(MultipartFile multipartFile);
    List<ImageDTO> getAllImages();
    Optional<ImageDTO> getImageById(Long imageId);
    int saveImage(ImageDTO imageDTO, MultipartFile image);
    int updateImage(ImageDTO imageDTO, MultipartFile image);
    int deleteImage(Long imageId);

    default ImageDTO convertToDTO(ImageEntity imageEntity) {
        ImageDTO imageDTO = ImageDTO.builder()
                .imageId(imageEntity.getImageId())
                .imageUrl(imageEntity.getImageUrl())
                .build();
        return imageDTO;
    }
}
