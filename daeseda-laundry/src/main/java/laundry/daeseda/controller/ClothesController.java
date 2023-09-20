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

    @GetMapping("/list")
    public ResponseEntity<List<ClothesDTO>> getAllClothes() {
        List<ClothesDTO> clothesDTOList = clothesService.getAllClothes();
        return ResponseEntity.ok().body(clothesDTOList);
    }

    @GetMapping("/{clothesId}")
    public ResponseEntity<ClothesDTO> getClothes(@PathVariable Long clothesId) {
        ClothesDTO clothesDTO = clothesService.getClothesById(clothesId).orElse(null);
        if (clothesDTO != null) {
            return ResponseEntity.ok(clothesDTO); // ok : 200(성공시 보내는 요청)
        } else {
            return ResponseEntity.notFound().build();   // notFound : 404(실패시 보내는 요청)
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerClothes(@RequestBody ClothesDTO clothesDTO) {
        clothesService.createClothes(clothesDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{clothesId}")
    public ResponseEntity<String> updateClothes(@RequestBody ClothesDTO clothesDTO) {
        if(clothesService.updateClothes(clothesDTO) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("업데이트가 성공적으로 실행되었습니다");
        }   // NO_CONTENT : 204(삭제나 수정시 보내는 요청)
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("업데이트에 실패했습니다.");
        }   // NOT_FOUND : 404(실패시 보내는 요청)
    }

    @DeleteMapping("/{clothesId}")
    public ResponseEntity<String> deleteClothes(@PathVariable Long clothesId) {
        if(clothesService.deleteClothes(clothesId) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("삭제가 성공적으로 실행되었습니다");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제에 실패했습니다.");
        }
    }
}
