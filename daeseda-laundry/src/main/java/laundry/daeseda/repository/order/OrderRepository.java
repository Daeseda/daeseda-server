package laundry.daeseda.repository.order;

import laundry.daeseda.entity.order.OrderEntity;
import laundry.daeseda.entity.user.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
