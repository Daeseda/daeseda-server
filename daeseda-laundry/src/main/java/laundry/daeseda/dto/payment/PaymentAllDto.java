package laundry.daeseda.dto.payment;

import laundry.daeseda.dto.order.OrderAllDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentAllDto {

    private Long cashId;
    private OrderAllDto order;
    private String cardName;
    private String cardNumber;
    private Long paidAmount;
    private String payMethod;

}
