package laundry.daeseda.controller;

import laundry.daeseda.dto.notice.NoticeDTO;
import laundry.daeseda.entity.notice.NoticeEntity;
import laundry.daeseda.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/list")
    public ResponseEntity<List<NoticeDTO>> getAllNotice() {
        List<NoticeDTO> notice = noticeService.getAllNotices();
        return ResponseEntity.ok().body(notice);
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeDTO> getNotice(@PathVariable Long noticeId) {
        NoticeDTO notice = noticeService.getNoticeById(noticeId).orElse(null);
        if(notice != null) {
            return ResponseEntity.ok(notice);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerNotice(@RequestBody NoticeDTO noticeDTO) {
        noticeService.createNotice(noticeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{noticeId}")
    public ResponseEntity<String> updateNotice(@RequestBody NoticeDTO noticeDTO) {
        if(noticeService.updateNotice(noticeDTO) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("업데이트가 완료되었습니다.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("업데이트에 실패하였습니다.");
        }
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<String> deleteNotice(@PathVariable Long noticeId) {
        if(noticeService.deleteNotice(noticeId) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("삭제가 성공적으로 실행되었습니다.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제에 실패했습니다.");
        }
    }
}
