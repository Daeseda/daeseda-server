package laundry.daeseda.repository.order;

import laundry.daeseda.entity.order.ClothesCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderClothesRepository extends JpaRepository<ClothesCountEntity, Long> {
}
