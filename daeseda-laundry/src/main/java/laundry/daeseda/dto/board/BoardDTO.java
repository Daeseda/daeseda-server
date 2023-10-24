package laundry.daeseda.dto.board;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import laundry.daeseda.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel(value = "게시글 정")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BoardDTO {
    @ApiModelProperty(value = "게시글 ID", example = "자동 할당")
    private Long boardId;

    @ApiModelProperty(value = "유저 ID", example = "토큰으로 자동 할당")
    private Long userId;

    @ApiModelProperty(value = "유저 닉네임", example = "토큰으로 자동 할당")
    private String userNickname;

    @ApiModelProperty(value = "게시글 카테고리", example = "카테고리")
    private String boardCategory;

    @ApiModelProperty(value = "게시글 제목", example = "게시글 제목")
    private String boardTitle;

    @ApiModelProperty(value = "게시글 내용", example = "게시글 내용")
    private String boardContent;

    @ApiModelProperty(value = "생성 시간", example = "자동 할당")
    private LocalDateTime regDate;

    @ApiModelProperty(value = "수정 시간", example = "자동 할당")
    private LocalDateTime modDate;
}
