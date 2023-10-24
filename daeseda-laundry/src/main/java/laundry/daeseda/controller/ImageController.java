package laundry.daeseda.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(tags = {"이미지 API 정보를 제공하는 Controller"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;


    @ApiOperation(value = "전체 이미지 URL 보는 메서드")
    @GetMapping("/list")
    public ResponseEntity<java.util.List<ImageDTO>> getAllImages() {
        List<ImageDTO> image = imageService.getAllImages();
        return ResponseEntity.ok().body(image);
    }

    @ApiOperation(value = "특정 이미지 URL 보는 메서드")
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

    @ApiOperation(value = "이미지 URL 생성하는 메서드")
    @PostMapping("/register")
    public ResponseEntity<Void> saveImage(@RequestParam("image") MultipartFile image, ImageDTO imageDTO) {
        int result = imageService.saveImage(imageDTO, image);

        if (result == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation(value = "특정 이미지 URL 수정하는 메서드")
    @PutMapping("/{imageId}")
    public ResponseEntity<String> updateImage(ImageDTO imageDTO, @RequestParam("image") MultipartFile image) {
        if(imageService.updateImage(imageDTO, image) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("업데이트가 완료되었습니다.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("업데이트에 실패하였습니다.");
        }
    }

    @ApiOperation(value = "특정 이미지 URL 삭제하는 메서드")
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
