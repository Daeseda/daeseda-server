package laundry.daeseda.dto.reply;

import laundry.daeseda.entity.board.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReplyDTO {
    private Long replyId;
    private Long userId;
    private String userNickname;
    private Long boardId;
    private String replyContent;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
