package laundry.daeseda.repository.payment;


import laundry.daeseda.entity.order.OrderEntity;
import laundry.daeseda.entity.payment.PaymentEntity;
import laundry.daeseda.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Modifying
    @Query("delete from PaymentEntity p WHERE p.order = :order")
    void deleteByOrderId(OrderEntity order);

}
