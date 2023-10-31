package laundry.daeseda.service.board;

import laundry.daeseda.dto.board.BoardDTO;
import laundry.daeseda.entity.board.BoardEntity;
import laundry.daeseda.entity.user.UserEntity;
import laundry.daeseda.repository.board.BoardRepository;
import laundry.daeseda.repository.user.UserRepository;
import laundry.daeseda.service.reply.ReplyService;
import laundry.daeseda.service.user.CustomUserDetailsService;
import laundry.daeseda.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final ReplyService replyService;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
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
        UserEntity userEntity = userRepository.findByUserEmail(SecurityUtil.getCurrentUsername().get())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        BoardEntity boardEntity = BoardEntity.builder()
                .boardId(boardDTO.getBoardId())
                .user(userEntity)
                .userNickname(userEntity.getUserNickname())
                .boardCategory(boardDTO.getBoardCategory())
                .boardTitle(boardDTO.getBoardTitle())
                .boardTitle(boardDTO.getBoardTitle())
                .boardContent(boardDTO.getBoardContent())
                .regDate(LocalDateTime.now())
                .modDate(LocalDateTime.now())
                .build();
        boardRepository.save(boardEntity);
        return 1;
    }

    @Override
    public int updateBoard(BoardDTO boardDTO) {
        UserEntity userEntity = userRepository.findByUserEmail(SecurityUtil.getCurrentUsername().get())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 기존 게시글 ID와 수정 요청 게시글의 ID가 같은지 확인
        BoardEntity board = boardRepository.findById(boardDTO.getBoardId())
                .orElseThrow(() -> new EntityNotFoundException("해당 ID에 해당하는 게시물을 찾을 수 없습니다."));

        if (board != null && board.getUser().getUserId().equals(userEntity.getUserId())) {
            BoardEntity boardEntity = BoardEntity.builder()
                    .boardId(board.getBoardId())
                    .user(userEntity)
                    .userNickname(userEntity.getUserNickname())
                    .boardCategory(boardDTO.getBoardCategory())
                    .boardTitle(boardDTO.getBoardTitle())
                    .boardContent(boardDTO.getBoardContent())
                    .modDate(LocalDateTime.now())
                    .build();
            boardRepository.save(boardEntity); // 수정된 게시글 저장
            return 1;
        } else {
            throw new AccessDeniedException("게시글 수정 권한이 없습니다.");
        }
    }


    @Override
    public int deleteBoard(Long boardId) {
        UserEntity userEntity = userRepository.findByUserEmail(SecurityUtil.getCurrentUsername().get())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 게시글 정보 가져오기
        Optional<BoardEntity> board = boardRepository.findById(boardId);

        if (board.isPresent()) {
            BoardEntity boardEntity = board.get();

            // 권한 확인: 현재 사용자가 게시글 작성자인 경우만 삭제 권한 부여
            if (userEntity.getUserId().equals(boardEntity.getUser().getUserId())) {
                try {
                    replyService.deleteRepliesByBoardId(boardId);
                    boardRepository.deleteById(boardId);
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
    }

}
