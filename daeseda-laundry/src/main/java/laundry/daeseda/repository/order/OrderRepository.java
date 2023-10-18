package laundry.daeseda.repository.order;

import laundry.daeseda.constant.OrderStatus;
import laundry.daeseda.dto.order.OrderAllDto;
import laundry.daeseda.entity.order.OrderEntity;
import laundry.daeseda.entity.user.AuthorityEntity;
import laundry.daeseda.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> getByUser(UserEntity user);

    @Modifying
    @Query("update OrderEntity o SET o.orderStatus = :orderStatus WHERE o.orderId = :orderId")
    void updateStatus(OrderStatus orderStatus, Long orderId);
}
