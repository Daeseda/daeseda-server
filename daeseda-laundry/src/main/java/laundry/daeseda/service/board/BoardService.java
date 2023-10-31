package laundry.daeseda.service.board;

import laundry.daeseda.dto.board.BoardDTO;
import laundry.daeseda.entity.board.BoardEntity;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    List<BoardDTO> getAllBoardes();
    Optional<BoardDTO> getBoardById(Long boardId);
    int createBoard(BoardDTO boardDTO);
    int updateBoard(BoardDTO boardDTO);
    int deleteBoard(Long boardId);

    default BoardDTO convertToDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = BoardDTO.builder()
                .boardId(boardEntity.getBoardId())
                .userId(boardEntity.getUser().getUserId())
                .userNickname(boardEntity.getUser().getUserNickname())
                .boardCategory(boardEntity.getBoardCategory())
                .boardTitle(boardEntity.getBoardTitle())
                .boardContent(boardEntity.getBoardContent())
                .regDate(boardEntity.getRegDate())
                .modDate(boardEntity.getModDate())
                .build();
        return boardDTO;
    }
}
