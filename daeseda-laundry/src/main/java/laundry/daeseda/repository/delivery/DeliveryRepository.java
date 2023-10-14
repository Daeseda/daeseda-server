package laundry.daeseda.repository.delivery;

import laundry.daeseda.entity.delivery.DeliveryEntity;
import laundry.daeseda.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
    void getByUser(UserEntity userEntity);
}
