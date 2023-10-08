package laundry.daeseda.dto.order;

import laundry.daeseda.constant.OrderStatus;
import laundry.daeseda.dto.address.AddressDto;
import laundry.daeseda.dto.address.AddressListDto;
import laundry.daeseda.dto.clothes.ClothesCountDto;
import laundry.daeseda.dto.clothes.ClothesDTO;
import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.entity.clothes.ClothesEntity;
import laundry.daeseda.entity.user.AddressEntity;
import laundry.daeseda.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Builder
public class OrderDto {

//    private UserDto user;
    private AddressDto address;
    private List<ClothesCountDto> clothesCount;

//    private OrderStatus orderStatus;
    private int totalPrice;
    private String washingMethod;
    private LocalDate pickupDate;
    private LocalDate deliveryDate;
    private String deliveryLocation;
}
