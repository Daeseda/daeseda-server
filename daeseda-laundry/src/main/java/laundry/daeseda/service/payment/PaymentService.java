package laundry.daeseda.service.payment;

import laundry.daeseda.dto.payment.PaymentDto;

public interface PaymentService {

    boolean registrationPayment(PaymentDto paymentDto);
}
