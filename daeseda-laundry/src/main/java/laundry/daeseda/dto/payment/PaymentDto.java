package laundry.daeseda.dto.payment;

import laundry.daeseda.dto.order.OrderRequestDto;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class PaymentDto {

    @NotNull
    private OrderRequestDto order;

    private String cardName;
    private String cardNumber;
    private Long paidAmount;
    private String payMethod;

}
