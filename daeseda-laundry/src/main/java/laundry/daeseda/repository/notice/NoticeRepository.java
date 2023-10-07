package laundry.daeseda.repository.notice;

import laundry.daeseda.entity.notice.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {
}
