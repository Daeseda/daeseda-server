package laundry.daeseda.controller;

import laundry.daeseda.dto.board.BoardDTO;
import laundry.daeseda.dto.notice.NoticeDTO;
import laundry.daeseda.dto.review.ImageDTO;
import laundry.daeseda.service.review.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;


    @GetMapping("/list")
    public ResponseEntity<java.util.List<ImageDTO>> getAllImages() {
        List<ImageDTO> image = imageService.getAllImages();
        return ResponseEntity.ok().body(image);
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<ImageDTO> getImage(@PathVariable Long imageId) {
        ImageDTO image = imageService.getImageById(imageId).orElse(null);
        if(image != null) {
            return ResponseEntity.ok(image);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> saveImage(@RequestParam("image") MultipartFile image, ImageDTO imageDTO) {
        int result = imageService.saveImage(imageDTO, image);

        if (result == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{imageId}")
    public ResponseEntity<String> updateImage(ImageDTO imageDTO, @RequestParam("image") MultipartFile image) {
        if(imageService.updateImage(imageDTO, image) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("업데이트가 완료되었습니다.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("업데이트에 실패하였습니다.");
        }
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<String> deleteImage(@PathVariable Long imageId) {
        if(imageService.deleteImage(imageId) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("삭제가 성공적으로 실행되었습니다.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제에 실패했습니다.");
        }
    }


}
