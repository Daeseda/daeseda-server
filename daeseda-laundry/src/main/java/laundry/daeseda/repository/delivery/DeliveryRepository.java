package laundry.daeseda.repository.delivery;

import laundry.daeseda.constant.DeliveryStatus;
import laundry.daeseda.constant.OrderStatus;
import laundry.daeseda.dto.order.OrderRequestDto;
import laundry.daeseda.entity.delivery.DeliveryEntity;
import laundry.daeseda.entity.order.OrderEntity;
import laundry.daeseda.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
    void getByUser(UserEntity userEntity);

    DeliveryEntity getByOrder(OrderEntity orderEntity);

    @Modifying
    @Query("update DeliveryEntity d SET d.deliveryStatus = :deliveryStatus WHERE d.order = :orderId")
    void updateStatus(DeliveryStatus deliveryStatus, Long orderId);
}
