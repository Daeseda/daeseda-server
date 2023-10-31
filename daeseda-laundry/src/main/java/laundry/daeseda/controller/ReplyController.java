package laundry.daeseda.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import laundry.daeseda.dto.reply.ReplyDTO;
import laundry.daeseda.service.reply.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"답글 API 정보를 제공하는 Controller"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    @ApiOperation(value = "답글 전체 목록을 보는 메서드")
    @GetMapping("/list")
    public ResponseEntity<List<ReplyDTO>> getAllReplies() {
        List<ReplyDTO> reply = replyService.getAllReplies();
        return ResponseEntity.ok().body(reply);
    }

    @ApiOperation(value = "특정 댓글을 보는 메서드")
    @GetMapping("/{replyId}")
    public ResponseEntity<ReplyDTO> getReply(@PathVariable Long replyId) {
        ReplyDTO reply = replyService.getReplyById(replyId).orElse(null);
        if(reply != null) {
            return ResponseEntity.ok(reply);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "댓글을 생성하는 메서드")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<Void> registerReply(@RequestBody ReplyDTO replyDTO) {
        replyService.createReply(replyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "특정 댓글을 수정하는 메서드")
    @PutMapping("/{replyId}")
    public ResponseEntity<String> updateReply(@RequestBody ReplyDTO replyDTO) {
        if(replyService.updateReply(replyDTO) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("업데이트가 완료되었습니다.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("업데이트에 실패하였습니다.");
        }
    }

    @ApiOperation(value = "특정 댓글을 삭제하는 메서드")
    @DeleteMapping("/{replyId}")
    public ResponseEntity<String> deleteReply(@PathVariable Long replyId) {
        if(replyService.deleteReply(replyId) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("삭제가 성공적으로 실행되었습니다.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제에 실패했습니다.");
        }
    }
}
