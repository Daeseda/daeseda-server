package laundry.daeseda.service.order;

import laundry.daeseda.dto.address.AddressListDto;
import laundry.daeseda.dto.clothes.ClothesDTO;
import laundry.daeseda.dto.order.OrderDto;
import laundry.daeseda.dto.order.OrderFormDto;
import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.repository.order.OrderRepository;
import laundry.daeseda.service.clothes.ClothesService;
import laundry.daeseda.service.user.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final AddressService addressService;
    private final ClothesService clothesService;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderFormDto getOrderForm() {
        OrderFormDto orderFormDto = OrderFormDto.builder()
                .address(addressService.getMyAddressList())
                .clothes(clothesService.getAllClothes())
                .build();
        return orderFormDto;
    }

    @Override
    public void requestOrder(OrderDto orderDto) {

    }
}
