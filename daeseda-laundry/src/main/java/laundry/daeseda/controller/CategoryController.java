package laundry.daeseda.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import laundry.daeseda.dto.category.CategoryDTO;
import laundry.daeseda.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(tags = {"카테고리 API 정보를 제공하는 Controller"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @ApiOperation(value = "카테고리 전체 목록을 보는 메서드")
    @GetMapping("/list")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok().body(categories);
    }

    @ApiOperation(value = "특정 카테고리를 보는 메서드")
    @ApiImplicitParam(name = "categoryId", value = "카테고리 ID")
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long categoryId) {
        CategoryDTO category = categoryService.getCategoryById(categoryId).orElse(null);
        if (category != null) {
            return ResponseEntity.ok(category); // ok : 200(성공시 보내는 요청)
        } else {
            return ResponseEntity.notFound().build();   // notFound : 404(실패시 보내는 요청)
        }
    }

    @ApiOperation(value = "카테고리를 생성하는 메서드")
    @ApiImplicitParam(name = "categoryDTO", value = "카테고리명")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<Void> registerCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
        // CREATED - 201(생성시 보내는 요청)
    }

    @ApiOperation(value = "특정 카테고리를 수정하는 메서드")
    @ApiImplicitParam(name = "categoryDTO", value = "카테고리 ID, 카테고리명")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryDTO categoryDTO) {
        if(categoryService.updateCategory(categoryDTO) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("업데이트가 성공적으로 실행되었습니다");
        }   // NO_CONTENT : 204(삭제나 수정시 보내는 요청)
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("업데이트에 실패했습니다.");
        }   // NOT_FOUND : 404(실패시 보내는 요청)
    }

    @ApiOperation(value = "특정 카테고리를 삭제하는 메서드")
    @ApiImplicitParam(name = "categoryId", value = "카테고리 ID")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        if(categoryService.deleteCategory(categoryId) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("삭제가 성공적으로 실행되었습니다");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제에 실패했습니다.");
        }
    }
}
