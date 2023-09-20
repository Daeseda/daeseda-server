package laundry.daeseda.dto.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "카테고리 생성 정보")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CategoryDTO {
    @ApiModelProperty(value = "카테고리 ID", example = "0")
    private Long categoryId;

    @ApiModelProperty(value = "카테고리명", example = "와이셔츠")
    private String categoryName;
}
