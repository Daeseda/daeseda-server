package laundry.daeseda.controller;

import laundry.daeseda.dto.category.CategoryDTO;
import laundry.daeseda.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/list")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long categoryId) {
        CategoryDTO category = categoryService.getCategoryById(categoryId).orElse(null);
        if (category != null) {
            return ResponseEntity.ok(category); // ok : 200(성공시 보내는 요청)
        } else {
            return ResponseEntity.notFound().build();   // notFound : 404(실패시 보내는 요청)
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
        // CREATED - 201(생성시 보내는 요청)
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryDTO categoryDTO) {
        if(categoryService.updateCategory(categoryDTO) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("업데이트가 성공적으로 실행되었습니다");
        }   // NO_CONTENT : 204(삭제나 수정시 보내는 요청)
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("업데이트에 실패했습니다.");
        }   // NOT_FOUND : 404(실패시 보내는 요청)
    }

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
