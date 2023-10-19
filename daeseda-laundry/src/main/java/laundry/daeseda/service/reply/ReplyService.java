package laundry.daeseda.service.reply;

import laundry.daeseda.dto.board.BoardDTO;
import laundry.daeseda.dto.reply.ReplyDTO;
import laundry.daeseda.entity.reply.ReplyEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReplyService {

    List<ReplyDTO> getAllReplies();
    Optional<ReplyDTO> getReplyById(Long replyId);
    int createReply(ReplyDTO replyDTO);
    int updateReply(ReplyDTO replyDTO);
    int deleteReply(Long replyId);
    int deleteRepliesByBoardId(Long boardId);


    default ReplyDTO convertToDTO(ReplyEntity replyEntity) {
        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyId(replyEntity.getReplyId())
                .userId(replyEntity.getUser().getUserId())
                .userNickname(replyEntity.getUser().getUserNickname())
                .boardId(replyEntity.getBoard().getBoardId())
                .replyContent(replyEntity.getReplyContent())
                .regDate(LocalDateTime.now())
                .modDate(LocalDateTime.now())
                .build();
        return replyDTO;
    }
}
