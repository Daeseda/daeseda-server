package laundry.daeseda.service.notice;

import laundry.daeseda.dto.category.CategoryDTO;
import laundry.daeseda.dto.notice.NoticeDTO;
import laundry.daeseda.entity.category.CategoryEntity;
import laundry.daeseda.entity.notice.NoticeEntity;
import laundry.daeseda.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    @Override
    public List<NoticeDTO> getAllNotices() {
        List<NoticeEntity> noticeList = noticeRepository.findAll();
        List<NoticeDTO> noticeDTOList = new ArrayList<>();
        for(NoticeEntity noticeEntity : noticeList) {
            NoticeDTO noticeDTO = convertToDto(noticeEntity);
            noticeDTOList.add(noticeDTO);
        }
        return noticeDTOList;
    }

    @Override
    public Optional<NoticeDTO> getNoticeById(Long noticeId) {
        try {
            Optional<NoticeEntity> noticeEntity = noticeRepository.findById(noticeId);
            if (noticeEntity.isPresent()) {
                NoticeDTO noticeDTO = convertToDto(noticeEntity.get());
                return Optional.of(noticeDTO);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            // 데이터베이스 조회 중 예외 발생 시 처리
            throw new RuntimeException("해당 공지사항이 없음", e);
        }
    }

    @Override
    public int createNotice(NoticeDTO noticeDTO) {
        NoticeEntity noticeEntity = convertToEntity(noticeDTO);
        if(noticeRepository.save(noticeEntity) != null) {
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public int updateNotice(NoticeDTO noticeDTO) {
        NoticeEntity noticeEntity = convertToEntity(noticeDTO);
        if(noticeEntity != null) {
            noticeRepository.save(noticeEntity);
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public int deleteNotice(Long noticeId) {
        try {
            noticeRepository.deleteById(noticeId);
            return 1; // 삭제 성공 시 1 반환
        } catch (Exception e) {
            return 0; // 삭제 실패 시 0 반환
        }
    }
}
