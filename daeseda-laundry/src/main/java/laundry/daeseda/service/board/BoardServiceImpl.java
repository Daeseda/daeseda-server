package laundry.daeseda.service.board;

import laundry.daeseda.dto.board.BoardDTO;
import laundry.daeseda.entity.board.BoardEntity;
import laundry.daeseda.repository.board.BoardRepository;
import laundry.daeseda.service.user.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    private final CustomUserDetailsService customUserDetailsService;
    @Override
    public List<BoardDTO> getAllBoardes() {
        List<BoardEntity> boardList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(BoardEntity boardEntity : boardList) {
            BoardDTO boardDTO = convertToDTO(boardEntity);
            boardDTOList.add(boardDTO);
        }
        return boardDTOList;
    }

    @Override
    public Optional<BoardDTO> getBoardById(Long boardId) {
        try {
            Optional<BoardEntity> boardEntity = boardRepository.findById(boardId);
            if (boardEntity.isPresent()) {
                BoardDTO boardDTO = convertToDTO(boardEntity.get());
                return Optional.of(boardDTO);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            // 데이터베이스 조회 중 예외 발생 시 처리
            throw new RuntimeException("해당 게시글이 없음", e);
        }
    }

    @Override
    public int createBoard(BoardDTO boardDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Long userId = customUserDetailsService.loadUserIdByEmail(authentication.getName());

            BoardEntity boardEntity = BoardEntity.builder()
                    .boardId(boardDTO.getBoardId())
                    .userId(userId)
                    .boardCategory(boardDTO.getBoardCategory())
                    .boardTitle(boardDTO.getBoardTitle())
                    .boardTitle(boardDTO.getBoardTitle())
                    .boardContent(boardDTO.getBoardContent())
                    .regDate(LocalDateTime.now())
                    .modDate(LocalDateTime.now())
                    .build();
            boardRepository.save(boardEntity); // 게시글 저장 및 반환
            if (boardRepository.save(boardEntity) != null) {
                return 1;
            }
        } else {
            throw new BadCredentialsException("로그인이 필요합니다.");
        }
        return 1;
    }

    @Override
    public int updateBoard(BoardDTO boardDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Long userId = customUserDetailsService.loadUserIdByEmail(authentication.getName());

            // 기존 게시글 ID와 수정 요청의 ID가 같은지 확인
            Long boardId = boardDTO.getBoardId();
            BoardEntity board = boardRepository.findById(boardId).orElse(null);

            if (board != null && board.getUserId().equals(userId)) {
                BoardEntity boardEntity = BoardEntity.builder()
                        .boardId(boardId)
                        .userId(userId)
                        .boardCategory(boardDTO.getBoardCategory())
                        .boardTitle(boardDTO.getBoardTitle())
                        .boardContent(board.getBoardContent())
                        .modDate(LocalDateTime.now())
                        .build();
                boardRepository.save(boardEntity); // 수정된 게시글 저장
                return 1;
            } else {
                throw new AccessDeniedException("게시글 수정 권한이 없습니다.");
            }
        } else {
            throw new BadCredentialsException("로그인이 필요합니다.");
        }
    }


    @Override
    public int deleteBoard(Long noticeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Long userId = customUserDetailsService.loadUserIdByEmail(authentication.getName());

            // 게시글 정보 가져오기
            Optional<BoardEntity> board = boardRepository.findById(noticeId);

            if (board.isPresent()) {
                BoardEntity boardEntity = board.get();

                // 권한 확인: 현재 사용자가 게시글 작성자인 경우만 삭제 권한 부여
                if (userId.equals(boardEntity.getUserId())) {
                    try {
                        boardRepository.deleteById(noticeId);
                        return 1;
                    } catch (Exception e) {
                        return 0;
                    }
                } else {
                    throw new AccessDeniedException("게시글 삭제 권한이 없습니다.");
                }
            } else {
                throw new EntityNotFoundException("게시글을 찾을 수 없습니다.");
            }
        } else {
            throw new BadCredentialsException("로그인이 필요합니다.");
        }
    }

}
