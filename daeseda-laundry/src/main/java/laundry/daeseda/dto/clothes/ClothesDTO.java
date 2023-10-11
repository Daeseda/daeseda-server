package laundry.daeseda.dto.clothes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import laundry.daeseda.entity.category.CategoryEntity;
import lombok.NoArgsConstructor;

@ApiModel(value = "의류 생성 정보")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ClothesDTO {
    @ApiModelProperty(value = "의류 ID", example = "0")
    private Long clothesId;

    @ApiModelProperty(value = "의류명", example = "검정 셔츠")
    private String clothesName;

    @ApiModelProperty(value = "의류 가격", example = "4000")
    private String clothesPrice;

    @ApiModelProperty(value = "카테고리 ID", example = "1")
    private Long categoryId;
}
