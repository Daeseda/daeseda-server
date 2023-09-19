package laundry.daeseda.dto.clothes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import laundry.daeseda.entity.category.CategoryEntity;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ClothesDTO {
    private Long clothesId;
    private String clothesName;
    private Long categoryId;
}
