package laundry.daeseda.repository.board;

import laundry.daeseda.entity.board.BoardEntity;
import laundry.daeseda.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    @Modifying
    @Query("delete from BoardEntity b WHERE b.user = :user")
    void deleteByUserId(UserEntity user);

}
