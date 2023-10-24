package laundry.daeseda.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import laundry.daeseda.dto.notice.NoticeDTO;
import laundry.daeseda.entity.notice.NoticeEntity;
import laundry.daeseda.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"공지사항 API 정보를 제공하는 Controller"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    @ApiOperation(value = "공지사항 전체 목록을 보는 메서드")
    @GetMapping("/list")
    public ResponseEntity<List<NoticeDTO>> getAllNotice() {
        List<NoticeDTO> notice = noticeService.getAllNotices();
        return ResponseEntity.ok().body(notice);
    }

    @ApiOperation(value = "특정 공지사항을 보는 메서드")
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

    @ApiOperation(value = "공지사항을 생성하는 메서드")
    @PostMapping("/register")
    public ResponseEntity<Void> registerNotice(@RequestBody NoticeDTO noticeDTO) {
        noticeService.createNotice(noticeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation(value = "특정 공지사항을 수정하는 메서드")
    @PutMapping("/{noticeId}")
    public ResponseEntity<String> updateNotice(@RequestBody NoticeDTO noticeDTO) {
        if(noticeService.updateNotice(noticeDTO) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("업데이트가 완료되었습니다.");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("업데이트에 실패하였습니다.");
        }
    }

    @ApiOperation(value = "특정 공지사항을 삭제하는 메서드")
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
