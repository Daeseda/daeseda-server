package laundry.daeseda.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import laundry.daeseda.dto.clothes.ClothesDTO;
import laundry.daeseda.service.clothes.ClothesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(tags = {"의류 API 정보를 제공하는 Controller"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/clothes")
public class ClothesController {
    private final ClothesService clothesService;

    @ApiOperation(value = "의류 전체 목록을 보는 메서드")
    @GetMapping("/list")
    public ResponseEntity<List<ClothesDTO>> getAllClothes() {
        List<ClothesDTO> clothesDTOList = clothesService.getAllClothes();
        return ResponseEntity.ok().body(clothesDTOList);
    }

    @ApiOperation(value = "특정 의류를 보는 메서드")
    @ApiImplicitParam(name = "clothesId", value = "의류 ID")
    @GetMapping("/{clothesId}")
    public ResponseEntity<ClothesDTO> getClothes(@PathVariable Long clothesId) {
        ClothesDTO clothesDTO = clothesService.getClothesById(clothesId).orElse(null);
        if (clothesDTO != null) {
            return ResponseEntity.ok(clothesDTO); // ok : 200(성공시 보내는 요청)
        } else {
            return ResponseEntity.notFound().build();   // notFound : 404(실패시 보내는 요청)
        }
    }

    @ApiOperation(value = "의류 생성하는 메서드")
    @ApiImplicitParam(name = "clothesDTO", value = "카테고리 ID, 의류명")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<String> registerClothes(@RequestBody ClothesDTO clothesDTO) {
        clothesService.createClothes(clothesDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "특정 의류를 수정하는 메서드")
    @ApiImplicitParam(name = "clothesDTO", value = "의류 ID,카테고리 ID,카테고리명")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @PutMapping("/{clothesId}")
    public ResponseEntity<String> updateClothes(@RequestBody ClothesDTO clothesDTO) {
        if(clothesService.updateClothes(clothesDTO) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("업데이트가 성공적으로 실행되었습니다");
        }   // NO_CONTENT : 204(삭제나 수정시 보내는 요청)
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("업데이트에 실패했습니다.");
        }   // NOT_FOUND : 404(실패시 보내는 요청)
    }

    @ApiOperation(value = "특정 의류를 삭제하는 메서드")
    @ApiImplicitParam(name = "clothesId", value = "의류 ID")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
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
