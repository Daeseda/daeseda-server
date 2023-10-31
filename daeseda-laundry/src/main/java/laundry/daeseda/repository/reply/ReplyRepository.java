package laundry.daeseda.repository.reply;

import laundry.daeseda.entity.reply.ReplyEntity;
import laundry.daeseda.entity.user.AddressEntity;
import laundry.daeseda.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {
    List<ReplyEntity> findByBoardBoardId(Long boardId);

    List<ReplyEntity> getByUser(UserEntity userEntity);

}
