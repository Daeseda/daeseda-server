package laundry.daeseda.service.payment;

import laundry.daeseda.dto.payment.PaymentAllDto;
import laundry.daeseda.dto.payment.PaymentDto;

import java.util.List;

public interface PaymentService {

    boolean registrationPayment(PaymentDto paymentDto);

    List<PaymentAllDto> getPaymentList();
}
