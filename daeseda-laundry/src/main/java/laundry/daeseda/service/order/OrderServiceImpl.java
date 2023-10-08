package laundry.daeseda.service.order;

import laundry.daeseda.constant.OrderStatus;
import laundry.daeseda.dto.address.AddressListDto;
import laundry.daeseda.dto.clothes.ClothesCountDto;
import laundry.daeseda.dto.clothes.ClothesDTO;
import laundry.daeseda.dto.order.OrderDto;
import laundry.daeseda.dto.order.OrderFormDto;
import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.entity.clothes.ClothesEntity;
import laundry.daeseda.entity.order.ClothesCountEntity;
import laundry.daeseda.entity.order.OrderEntity;
import laundry.daeseda.entity.user.AddressEntity;
import laundry.daeseda.entity.user.AuthorityEntity;
import laundry.daeseda.entity.user.UserEntity;
import laundry.daeseda.repository.clothes.ClothesRepository;
import laundry.daeseda.repository.order.OrderClothesRepository;
import laundry.daeseda.repository.order.OrderRepository;
import laundry.daeseda.repository.user.AddressRepository;
import laundry.daeseda.repository.user.UserRepository;
import laundry.daeseda.service.clothes.ClothesService;
import laundry.daeseda.service.user.AddressService;
import laundry.daeseda.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final AddressService addressService;
    private final ClothesService clothesService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final OrderClothesRepository orderClothesRepository;
    private final ClothesRepository clothesRepository;

    @Transactional
    public OrderFormDto getOrderForm() {
        OrderFormDto orderFormDto = OrderFormDto.builder()
                .address(addressService.getMyAddressList())
                .clothes(clothesService.getAllClothes())
                .build();
        return orderFormDto;
    }

    @Transactional
    public void requestOrder(OrderDto orderDto) {

        String currentUserEmail = SecurityUtil.getCurrentUsername().get();
        UserEntity currentUser = userRepository.findByUserEmail(currentUserEmail).orElse(null);

        if (currentUser != null) {
            AddressEntity address = addressRepository.findById(orderDto.getAddress().getAddressId())
                    .orElseThrow(() -> new EntityNotFoundException("주소를 찾을 수 없습니다. ID: " + orderDto.getAddress().getAddressId()));


            OrderEntity orderEntity = OrderEntity.builder()
                    .user(currentUser)
                    .address(address)
                    .deliveryLocation(orderDto.getDeliveryLocation())
                    .totalPrice(orderDto.getTotalPrice())
                    .orderStatus(OrderStatus.ORDER)
                    .washingMethod(orderDto.getWashingMethod())
                    .pickupDate(orderDto.getPickupDate())
                    .deliveryDate(orderDto.getDeliveryDate())
                    .build();
            orderEntity = orderRepository.save(orderEntity);


            List<ClothesCountDto> clothesCountDtoList = orderDto.getClothesCount();
            System.out.println(clothesCountDtoList != null);
            if (clothesCountDtoList != null) {
                for (ClothesCountDto clothesCountDto : clothesCountDtoList) {
                    ClothesEntity clothesEntity = clothesRepository.findById(clothesCountDto.getClothes().getClothesId())
                            .orElseThrow(() -> new EntityNotFoundException("의류를 찾을 수 없습니다. ID: " + clothesCountDto.getClothes().getClothesId()));

                    ClothesCountEntity clothesCount = ClothesCountEntity.builder()
                            .clothes(clothesEntity)
                            .count(clothesCountDto.getCount())
                            .order(orderEntity)
                            .build();
                    orderClothesRepository.save(clothesCount);
                }
            }
        }
    }
}
