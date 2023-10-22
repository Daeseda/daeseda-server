package laundry.daeseda.service.payment;

import laundry.daeseda.constant.OrderStatus;
import laundry.daeseda.dto.payment.PaymentDto;
import laundry.daeseda.entity.order.OrderEntity;
import laundry.daeseda.entity.payment.PaymentEntity;
import laundry.daeseda.repository.order.OrderRepository;
import laundry.daeseda.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public boolean registrationPayment(PaymentDto paymentDto) {
        OrderEntity orderEntity = orderRepository.getById(paymentDto.getOrder().getOrderId());
        if(orderEntity != null) {
            PaymentEntity paymentEntity = PaymentEntity.builder()
                    .order(orderEntity)
                    .cardName(paymentDto.getCardName())
                    .cardNumber(paymentDto.getCardNumber())
                    .paidAmount(paymentDto.getPaidAmount())
                    .payMethod(paymentDto.getPayMethod())
                    .build();
            paymentRepository.save(paymentEntity);
            OrderStatus orderStatus = OrderStatus.COMPLETE;
            orderRepository.updateStatus(orderStatus, paymentDto.getOrder().getOrderId());
        }
        return false;
    }
}
