package laundry.daeseda.service.board;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import laundry.daeseda.dto.board.BoardDTO;
import laundry.daeseda.dto.notice.NoticeDTO;
import laundry.daeseda.entity.board.BoardEntity;
import laundry.daeseda.service.user.CustomUserDetailsService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
                .userId(boardEntity.getUserId())
                .boardCategory(boardEntity.getBoardCategory())
                .boardTitle(boardEntity.getBoardTitle())
                .boardContent(boardEntity.getBoardContent())
                .regDate(boardEntity.getRegDate())
                .modDate(boardEntity.getModDate())
                .build();
        return boardDTO;
    }
}
