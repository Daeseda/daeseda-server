package laundry.daeseda.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import laundry.daeseda.dto.board.BoardDTO;
import laundry.daeseda.dto.notice.NoticeDTO;
import laundry.daeseda.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"게시글 API 정보를 제공하는 Controller"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @ApiOperation(value = "게시글 전체를 보는 메서드")
    @GetMapping("/list")
    public ResponseEntity<List<BoardDTO>> getAllBoards() {
        List<BoardDTO> board = boardService.getAllBoardes();
        return ResponseEntity.ok().body(board);
    }

    @ApiOperation(value = "특정 게시글 보는 메서드")
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

    @ApiOperation(value = "게시글을 생성하는 메서드")
    @PostMapping("/register")
    public ResponseEntity<Void> registerBoard(@RequestBody BoardDTO boardDTO) {
        boardService.createBoard(boardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "특정 게시글 수정하는 메서드")
    @PutMapping("/{boardId}")
    public ResponseEntity<String> updateBoard(@RequestBody BoardDTO boardDTO) {
        if(boardService.updateBoard(boardDTO) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("업데이트가 완료되었습니다.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("업데이트에 실패하였습니다.");
        }
    }

    @ApiOperation(value = "게시글을 삭제하는 메서드")
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
