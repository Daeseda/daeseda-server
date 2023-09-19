package laundry.daeseda.controller;

import laundry.daeseda.dto.clothes.ClothesDTO;
import laundry.daeseda.service.clothes.ClothesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clothes")
public class ClothesController {
    private final ClothesService clothesService;


    @PostMapping("/register")
    public ResponseEntity<String> registerClothes(@RequestBody ClothesDTO clothesDTO) {
        clothesService.createClothes(clothesDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{clothesId}")
    public ResponseEntity<ClothesDTO> getClothes(@PathVariable Long clothesId) {
        ClothesDTO clothesDTO = clothesService.getClothesById(clothesId).orElse(null);
        if (clothesDTO != null) {
            return ResponseEntity.ok(clothesDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<ClothesDTO>> getAllClothes() {
        List<ClothesDTO> clothesDTOList = clothesService.getAllClothes();
        return ResponseEntity.ok().body(clothesDTOList);
    }

    @DeleteMapping("/{clothesId}")
    public ResponseEntity<String> deleteClothes(@PathVariable Long clothesId) {
        clothesService.deleteClothes(clothesId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{clothesId}")
    public ResponseEntity<String> updateClothes(@PathVariable Long clothesId, @RequestBody ClothesDTO clothesDTO) {
        clothesService.updateClothes(clothesId, clothesDTO);
        return ResponseEntity.ok().build();
    }
}
