package laundry.daeseda.dto.delivery;

import laundry.daeseda.dto.address.AddressDto;
import laundry.daeseda.dto.order.OrderAllDto;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class DeliveryDto {

    private OrderAllDto order;

}
