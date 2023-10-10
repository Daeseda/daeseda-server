package laundry.daeseda.repository.board;

import laundry.daeseda.entity.board.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
