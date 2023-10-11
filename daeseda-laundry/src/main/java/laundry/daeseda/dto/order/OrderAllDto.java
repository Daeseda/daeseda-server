package laundry.daeseda.dto.order;

import laundry.daeseda.constant.OrderStatus;
import laundry.daeseda.dto.address.AddressDto;
import laundry.daeseda.dto.clothes.ClothesCountDto;
import laundry.daeseda.dto.user.UserDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class OrderAllDto {

    private Long orderId;
    private UserDto user;
    private AddressDto address;
    private List<ClothesCountDto> clothesCount;

    private OrderStatus orderStatus;
    private Long totalPrice;
    private String washingMethod;
    private LocalDate pickupDate;
    private LocalDate deliveryDate;
    private String deliveryLocation;

}
