package laundry.daeseda.dto.order;

import laundry.daeseda.dto.address.AddressDto;
import laundry.daeseda.dto.clothes.ClothesCountDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class OrderDto {

    private AddressDto address;
    private List<ClothesCountDto> clothesCount;

    private Long totalPrice;
    private String washingMethod;
    private LocalDate pickupDate;
    private LocalDate deliveryDate;
    private String deliveryLocation;
}
