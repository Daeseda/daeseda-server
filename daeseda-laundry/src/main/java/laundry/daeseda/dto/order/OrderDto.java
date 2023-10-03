package laundry.daeseda.dto.order;

import laundry.daeseda.constant.OrderStatus;
import laundry.daeseda.dto.address.AddressDto;
import laundry.daeseda.dto.clothes.ClothesDTO;
import laundry.daeseda.entity.clothes.ClothesEntity;
import laundry.daeseda.entity.user.AddressEntity;
import laundry.daeseda.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private UserEntity user;
    private AddressDto address;
//    private Set<ClothesDTO> clothes;

    private OrderStatus orderStatus;
    private int totalPrice;
    private String washingMethod;
    private LocalDate pickupDate;
    private LocalDate deliveryDate;
    private String deliveryLocation;
}
