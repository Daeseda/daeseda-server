package laundry.daeseda.repository.reply;

import laundry.daeseda.entity.reply.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {
    List<ReplyEntity> findByBoardBoardId(Long boardId);
}
