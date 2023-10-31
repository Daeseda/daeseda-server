package laundry.daeseda.repository.order;

import laundry.daeseda.entity.order.ClothesCountEntity;
import laundry.daeseda.entity.order.OrderEntity;
import laundry.daeseda.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderClothesRepository extends JpaRepository<ClothesCountEntity, Long> {
    List<ClothesCountEntity> getByOrder(OrderEntity orderEntity);

}
