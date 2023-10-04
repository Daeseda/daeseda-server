package laundry.daeseda.service.notice;

import laundry.daeseda.dto.category.CategoryDTO;
import laundry.daeseda.dto.notice.NoticeDTO;
import laundry.daeseda.entity.notice.NoticeEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NoticeService {
    List<NoticeDTO> getAllNotices();
    Optional<NoticeDTO> getNoticeById(Long noticeId);
    int createNotice(NoticeDTO noticeDTO);
    int updateNotice(NoticeDTO noticeDTO);
    int deleteNotice(Long noticeId);

    default NoticeEntity convertToEntity(NoticeDTO noticeDTO) {
        NoticeEntity noticeEntity = NoticeEntity.builder()
                .noticeId(noticeDTO.getNoticeId())
                .noticeCategory(noticeDTO.getNoticeCategory())
                .noticeTitle(noticeDTO.getNoticeTitle())
                .noticeContent(noticeDTO.getNoticeContent())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        return noticeEntity;
    }

    default NoticeDTO convertToDto(NoticeEntity noticeEntity) {
        NoticeDTO noticeDTO = NoticeDTO.builder()
                .noticeId(noticeEntity.getNoticeId())
                .noticeCategory(noticeEntity.getNoticeCategory())
                .noticeTitle(noticeEntity.getNoticeTitle())
                .noticeContent(noticeEntity.getNoticeContent())
                .build();
        return noticeDTO;
    }
}
