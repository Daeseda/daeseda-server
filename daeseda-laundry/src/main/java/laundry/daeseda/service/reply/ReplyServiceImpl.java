package laundry.daeseda.service.reply;

import laundry.daeseda.dto.board.BoardDTO;
import laundry.daeseda.dto.reply.ReplyDTO;
import laundry.daeseda.entity.board.BoardEntity;
import laundry.daeseda.entity.reply.ReplyEntity;
import laundry.daeseda.repository.board.BoardRepository;
import laundry.daeseda.repository.reply.ReplyRepository;
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
public class ReplyServiceImpl implements ReplyService{
    private final ReplyRepository replyRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final BoardRepository boardRepository;

    @Override
    public List<ReplyDTO> getAllReplies() {
        List<ReplyEntity> replyList = replyRepository.findAll();
        List<ReplyDTO> replyDTOList = new ArrayList<>();
        for(ReplyEntity replyEntity : replyList) {
            ReplyDTO replyDTO = convertToDTO(replyEntity);
            replyDTOList.add(replyDTO);
        }
        return replyDTOList;
    }

    @Override
    public Optional<ReplyDTO> getReplyById(Long replyId) {
        try {
            Optional<ReplyEntity> replyEntity = replyRepository.findById(replyId);
            if (replyEntity.isPresent()) {
                ReplyDTO replyDTO = convertToDTO(replyEntity.get());
                return Optional.of(replyDTO);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            // 데이터베이스 조회 중 예외 발생 시 처리
            throw new RuntimeException("해당 댓글이 없음", e);
        }
    }

    @Override
    public int createReply(ReplyDTO replyDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Long userId = customUserDetailsService.loadUserIdByEmail(authentication.getName());

            Long boardId = replyDTO.getBoardId();

            BoardEntity boardEntity = boardRepository.findById(boardId)
                    .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

            ReplyEntity replyEntity = ReplyEntity.builder()
                    .replyId(replyDTO.getReplyId())
                    .userId(userId)
                    .board(boardEntity)
                    .replyContent(replyDTO.getReplyContent())
                    .regDate(LocalDateTime.now())
                    .modDate(LocalDateTime.now())
                    .build();
            replyRepository.save(replyEntity); // 게시글 저장 및 반환
            if (replyRepository.save(replyEntity) != null) {
                return 1;
            }
        } else {
            throw new BadCredentialsException("로그인이 필요합니다.");
        }
        return 1;
    }

    @Override
    public int updateReply(ReplyDTO replyDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Long userId = customUserDetailsService.loadUserIdByEmail(authentication.getName());

            Long replyId = replyDTO.getReplyId();
            ReplyEntity reply = replyRepository.findById(replyId).orElseThrow(null);

            Long boardId = replyDTO.getBoardId();
            BoardEntity boardEntity = boardRepository.findById(boardId)
                    .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

            if(reply != null && reply.getUserId().equals(userId)) {
                ReplyEntity replyEntity = ReplyEntity.builder()
                        .replyId(replyId)
                        .userId(userId)
                        .board(boardEntity)
                        .replyContent(replyDTO.getReplyContent())
                        .regDate(LocalDateTime.now())
                        .modDate(LocalDateTime.now())
                        .build();
                replyRepository.save(replyEntity);
                return 1;
            } else {
                throw new AccessDeniedException("댓글을 수정 권한이 없습니다.");
            }
        } else {
            throw new BadCredentialsException("로그인이 필요합니다.");
        }
    }

    @Override
    public int deleteReply(Long replyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Long userId = customUserDetailsService.loadUserIdByEmail(authentication.getName());

            Optional<ReplyEntity> reply = replyRepository.findById(replyId);

            if (reply.isPresent()) {
                ReplyEntity replyEntity = reply.get();

                // 권한 확인: 현재 사용자가 게시글 작성자인 경우만 삭제 권한 부여
                if (userId.equals(replyEntity.getUserId())) {
                    try {
                        replyRepository.deleteById(replyId);
                        return 1;
                    } catch (Exception e) {
                        return 0;
                    }
                } else {
                    throw new AccessDeniedException("댓글 삭제 권한이 없습니다.");
                }
            } else {
                throw new EntityNotFoundException("댓글을 찾을 수 없습니다.");
            }
        } else {
            throw new BadCredentialsException("로그인이 필요합니다.");
        }
    }

    @Override
    public List<ReplyDTO> deleteRepliesByBoardId(Long boardId) {
        // 특정 게시글의 댓글을 조회합니다.
        List<ReplyEntity> replyList = replyRepository.findByBoardBoardId(boardId);

        List<ReplyDTO> replyDTOList = new ArrayList<>();

        // 조회된 댓글을 삭제하고 DTO로 변환합니다.
        for (ReplyEntity replyEntity : replyList) {
            ReplyDTO replyDTO = convertToDTO(replyEntity);
            replyDTOList.add(replyDTO);

            // 댓글을 삭제합니다.
            replyRepository.delete(replyEntity);
        }

        return replyDTOList;
    }



}
