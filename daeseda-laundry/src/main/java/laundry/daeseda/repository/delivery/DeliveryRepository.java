package laundry.daeseda.repository.delivery;

import laundry.daeseda.constant.DeliveryStatus;
import laundry.daeseda.constant.OrderStatus;
import laundry.daeseda.dto.order.OrderRequestDto;
import laundry.daeseda.entity.delivery.DeliveryEntity;
import laundry.daeseda.entity.order.OrderEntity;
import laundry.daeseda.entity.user.UserEntity;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {

    DeliveryEntity getByOrder(OrderEntity orderEntity);

    @Modifying
    @Query("update DeliveryEntity d SET d.deliveryStatus = :deliveryStatus WHERE d.order = :order")
    void updateStatus(DeliveryStatus deliveryStatus, OrderEntity order);

    @Modifying
    @Query("delete from DeliveryEntity d WHERE d.order = :order")
    void deleteByOrderId(OrderEntity order);

}
