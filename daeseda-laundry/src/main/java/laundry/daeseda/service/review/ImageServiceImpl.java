package laundry.daeseda.service.review;

import laundry.daeseda.dto.board.BoardDTO;
import laundry.daeseda.dto.review.ImageDTO;
import laundry.daeseda.entity.board.BoardEntity;
import laundry.daeseda.entity.review.ImageEntity;
import laundry.daeseda.repository.review.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    @Value("${custom.image-directory}")
    private String imageDir;

    private final ImageRepository imageRepository;
    @Override
    public List<ImageDTO> getAllImages() {
        return null;
    }

    @Override
    public Optional<ImageDTO> getImageById(Long imageId) {
        try {
            Optional<ImageEntity> imageEntity = imageRepository.findById(imageId);
            if (imageEntity.isPresent()) {
                ImageDTO imageDTO = convertToDTO(imageEntity.get());
                return Optional.of(imageDTO);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            // 데이터베이스 조회 중 예외 발생 시 처리
            throw new RuntimeException("해당 이미지가 없음", e);
        }
    }


    @Override
    public int saveImage(ImageDTO imageDTO, MultipartFile image) {
        try {
            // 이미지를 서버에 저장할 경로 설정
            String uploadDirectory = imageDir; // 서버에 이미지를 저장할 디렉토리 경로

            // 원래 파일 이름 가져오기
            String originalFilename = image.getOriginalFilename();

            // 파일 확장자 추출
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // 이미지 파일에 고유한 이름 부여
            String uniqueFileName = UUID.randomUUID() + fileExtension;

            // 저장할 경로 및 파일명 조합
            String filePath = uploadDirectory + "/" + uniqueFileName;

            // 파일을 저장
            File dest = new File(filePath);
            image.transferTo(dest);

            // 저장된 파일의 URL을 반환
            String imageUrl = imageDir + "/" + uniqueFileName; // 실제 URL로 변경

            ImageEntity imageEntity = ImageEntity.builder()
                    .imageId(imageDTO.getImageId())
                    .imageUrl(imageUrl)
                    .build();
            imageRepository.save(imageEntity);
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateImage(ImageDTO imageDTO) {
        return 0;
    }

    @Override
    public int deleteImage(Long imageId) {
        return 0;
    }
}
