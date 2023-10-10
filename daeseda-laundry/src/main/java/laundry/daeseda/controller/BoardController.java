package laundry.daeseda.controller;

import laundry.daeseda.dto.board.BoardDTO;
import laundry.daeseda.dto.notice.NoticeDTO;
import laundry.daeseda.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<List<BoardDTO>> getAllBoards() {
        List<BoardDTO> board = boardService.getAllBoardes();
        return ResponseEntity.ok().body(board);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDTO> getBoard(@PathVariable Long boardId) {
        BoardDTO board = boardService.getBoardById(boardId).orElse(null);
        if(board != null) {
            return ResponseEntity.ok(board);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerBoard(@RequestBody BoardDTO boardDTO) {
        boardService.createBoard(boardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<String> updateBoard(@RequestBody BoardDTO boardDTO) {
        if(boardService.updateBoard(boardDTO) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("업데이트가 완료되었습니다.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("업데이트에 실패하였습니다.");
        }
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteNotice(@PathVariable Long boardId) {
        if(boardService.deleteBoard(boardId) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("삭제가 성공적으로 실행되었습니다.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제에 실패했습니다.");
        }
    }
}
