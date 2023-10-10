package laundry.daeseda.controller;

import laundry.daeseda.dto.reply.ReplyDTO;
import laundry.daeseda.service.reply.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/list")
    public ResponseEntity<List<ReplyDTO>> getAllReplies() {
        List<ReplyDTO> reply = replyService.getAllReplies();
        return ResponseEntity.ok().body(reply);
    }

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

    @PostMapping("/register")
    public ResponseEntity<Void> registerReply(@RequestBody ReplyDTO replyDTO) {
        replyService.createReply(replyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{replyId}")
    public ResponseEntity<String> updateReply(@RequestBody ReplyDTO replyDTO) {
        if(replyService.updateReply(replyDTO) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("업데이트가 완료되었습니다.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("업데이트에 실패하였습니다.");
        }
    }

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
