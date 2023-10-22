package laundry.daeseda.service.delivery;

import laundry.daeseda.constant.DeliveryStatus;
import laundry.daeseda.dto.address.AddressDto;
import laundry.daeseda.dto.delivery.DeliveryAllDto;
import laundry.daeseda.dto.delivery.DeliveryDto;
import laundry.daeseda.dto.order.OrderAllDto;
import laundry.daeseda.dto.order.OrderRequestDto;
import laundry.daeseda.dto.user.UserUpdateDto;
import laundry.daeseda.entity.delivery.DeliveryEntity;
import laundry.daeseda.entity.order.ClothesCountEntity;
import laundry.daeseda.entity.order.OrderEntity;
import laundry.daeseda.entity.user.AddressEntity;
import laundry.daeseda.entity.user.UserEntity;
import laundry.daeseda.repository.delivery.DeliveryRepository;
import laundry.daeseda.repository.order.OrderClothesRepository;
import laundry.daeseda.repository.order.OrderRepository;
import laundry.daeseda.repository.user.AddressRepository;
import laundry.daeseda.repository.user.UserRepository;
import laundry.daeseda.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService{

    private final DeliveryRepository deliveryRepository;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final OrderClothesRepository orderClothesRepository;
    private final UserRepository userRepository;

    @Override
    public DeliveryAllDto getDeliveryTrackingHistory(Long orderId) {
        UserEntity userEntity = userRepository.findByUserEmail(SecurityUtil.getCurrentUsername().get())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        OrderEntity orderEntity = orderRepository.findById(orderId).get();
        DeliveryEntity deliveryEntity = deliveryRepository.getByOrder(orderEntity);
        if(deliveryEntity != null){
            UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                    .userName(userEntity.getUserName())
                    .userNickname(userEntity.getUserNickname())
                    .userPhone(userEntity.getUserPhone())
                    .build();

            AddressDto addressDto = AddressDto.builder()
                    .addressId(orderEntity.getAddress().getAddressId())
                    .addressRoad(orderEntity.getAddress().getAddressRoad())
                    .addressDetail(orderEntity.getAddress().getAddressDetail())
                    .addressName(orderEntity.getAddress().getAddressName())
                    .addressZipcode(orderEntity.getAddress().getAddressZipcode())
                    .build();

            OrderAllDto orderAllDto = OrderAllDto.builder()
                    .orderId(orderEntity.getOrderId())
                    .build();

            DeliveryAllDto deliveryAllDto = DeliveryAllDto.builder()
                    .user(userUpdateDto)
                    .address(addressDto)
                    .order(orderAllDto)
                    .deliveryStatus(deliveryEntity.getDeliveryStatus())
                    .build();

            return deliveryAllDto;
        }
        return null;
    }

    @Transactional
    public int requestDelivery(DeliveryDto deliveryDto) {

        OrderEntity orderEntity = OrderEntity.builder()
                .orderId(deliveryDto.getOrder().getOrderId())
                .build();

        if(deliveryRepository.getByOrder(orderEntity) == null){
            AddressEntity addressEntity = addressRepository.findById(deliveryDto.getAddress().getAddressId()).get();

            UserEntity userEntity = userRepository.findByUserEmail(SecurityUtil.getCurrentUsername().get())
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

            if(addressEntity.getUser().getUserId() == userEntity.getUserId()){

                DeliveryEntity deliveryEntity = DeliveryEntity.builder()
                        .user(userEntity)
                        .address(addressEntity)
                        .order(orderEntity)
                        .deliveryStatus(DeliveryStatus.READY)
                        .build();

                deliveryRepository.save(deliveryEntity);
                return 1;
            }
        }

        return 0;
    }
}
