package laundry.daeseda.controller;

import laundry.daeseda.dto.board.BoardDTO;
import laundry.daeseda.dto.review.ImageDTO;
import laundry.daeseda.service.review.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;


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


}
