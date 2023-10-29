package laundry.daeseda.dto.delivery;

import laundry.daeseda.constant.DeliveryStatus;
import laundry.daeseda.dto.address.AddressDto;
import laundry.daeseda.dto.order.OrderAllDto;
import laundry.daeseda.dto.user.UserUpdateDto;
import laundry.daeseda.entity.user.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeliveryAllDto {

    private String address;
    private UserUpdateDto user;
    private OrderAllDto order;
    private DeliveryStatus deliveryStatus;

}
