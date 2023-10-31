package laundry.daeseda.repository.reply;

import laundry.daeseda.entity.reply.ReplyEntity;
import laundry.daeseda.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {
    List<ReplyEntity> findByBoardBoardId(Long boardId);

    @Modifying
    @Query("delete from AddressEntity a WHERE a.user = :user")
    void deleteByUserId(UserEntity user);
}
