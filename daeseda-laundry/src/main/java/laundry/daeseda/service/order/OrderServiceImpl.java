package laundry.daeseda.service.order;

import laundry.daeseda.constant.OrderStatus;
import laundry.daeseda.dto.address.AddressDto;
import laundry.daeseda.dto.clothes.ClothesCountDto;
import laundry.daeseda.dto.clothes.ClothesDTO;
import laundry.daeseda.dto.order.OrderAllDto;
import laundry.daeseda.dto.order.OrderDto;
import laundry.daeseda.dto.order.OrderFormDto;
import laundry.daeseda.dto.order.OrderWithdrawDto;
import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.entity.clothes.ClothesEntity;
import laundry.daeseda.entity.order.ClothesCountEntity;
import laundry.daeseda.entity.order.OrderEntity;
import laundry.daeseda.entity.user.AddressEntity;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void withdrawOrder(OrderWithdrawDto orderWithdrawDto) {
        orderRepository.deleteById(orderWithdrawDto.getOrderId());
    }

    @Override
    public void getOrderDetail() {

    }

    @Override
    public List<OrderAllDto> getUserOrderList() {
      
        UserEntity userEntity = userRepository.findOneWithAuthoritiesByUserEmail(SecurityUtil.getCurrentUsername().get()).get();
      
        List<OrderEntity> orderEntityList = orderRepository.getByUser(userEntity);
        List<OrderAllDto> orderAllDtoList = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntityList) {
            List<ClothesCountEntity> clothesCountEntities = orderClothesRepository.getByOrder(orderEntity);
            List<ClothesCountDto> clothesCountDtoList = new ArrayList<>();
            for(ClothesCountEntity clothesCountEntity : clothesCountEntities) {
                ClothesDTO clothesDTO = ClothesDTO.builder()
                        .clothesId(clothesCountEntity.getClothes().getClothesId())
                        .clothesName(clothesCountEntity.getClothes().getClothesName())
                        .clothesPrice(clothesCountEntity.getClothes().getClothesPrice())
                        .categoryId(clothesCountEntity.getClothes().getCategory().getCategoryId())
                        .build();
                
                ClothesCountDto clothesCountDto = ClothesCountDto.builder()
                        .clothes(clothesDTO)
                        .count(clothesCountEntity.getCount())
                        .build();
                
                clothesCountDtoList.add(clothesCountDto);
            }

            OrderAllDto orderAllDto = OrderAllDto.builder()
                    .orderId(orderEntity.getOrderId())
                    .user(UserDto.from(userEntity))
                    .address(AddressDto.from(orderEntity.getAddress()))
                    .deliveryLocation(orderEntity.getDeliveryLocation())
                    .totalPrice(orderEntity.getTotalPrice())
                    .orderStatus(OrderStatus.ORDER)
                    .washingMethod(orderEntity.getWashingMethod())
                    .pickupDate(orderEntity.getPickupDate())
                    .deliveryDate(orderEntity.getDeliveryDate())
                    .clothesCount(clothesCountDtoList)
                    .build();
            orderAllDtoList.add(orderAllDto);
        }
        return orderAllDtoList;
    }

    @Transactional
    public void patchStatus(OrderWithdrawDto orderAllDto) {
        OrderStatus orderStatus = OrderStatus.CASH;
        orderRepository.updateStatus(orderStatus, orderAllDto.getOrderId());
    }
}
