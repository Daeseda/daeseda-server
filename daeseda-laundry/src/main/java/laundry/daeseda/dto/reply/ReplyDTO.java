package laundry.daeseda.dto.reply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import laundry.daeseda.entity.board.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel(value = "댓글 정")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReplyDTO {
    @ApiModelProperty(value = "댓글 ID", example = "자동 할당")
    private Long replyId;

    @ApiModelProperty(value = "유저 ID", example = "토큰으로 자동 할당")
    private Long userId;

    @ApiModelProperty(value = "유저 닉네임", example = "토큰으로 자동 할당")
    private String userNickname;

    @ApiModelProperty(value = "게시글 ID", example = "1")
    private Long boardId;

    @ApiModelProperty(value = "댓글 내용", example = "댓글")
    private String replyContent;

    @ApiModelProperty(value = "생성 시간", example = "자동 할당")
    private LocalDateTime regDate;

    @ApiModelProperty(value = "수정 시간", example = "자동 할당")
    private LocalDateTime modDate;
}
