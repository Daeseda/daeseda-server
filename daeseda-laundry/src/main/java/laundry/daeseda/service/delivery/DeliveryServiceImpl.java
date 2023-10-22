package laundry.daeseda.service.delivery;

import laundry.daeseda.constant.DeliveryStatus;
import laundry.daeseda.dto.delivery.DeliveryDto;
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

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService{

    private final DeliveryRepository deliveryRepository;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final OrderClothesRepository orderClothesRepository;
    private final UserRepository userRepository;

    @Override
    public void getDeliveryTrackingList() {
        UserEntity userEntity = userRepository.findByUserEmail(SecurityUtil.getCurrentUsername().get())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        deliveryRepository.getByUser(userEntity);
    }

    @Override
    public int requestDelivery(DeliveryDto deliveryDto) {
        UserEntity userEntity = userRepository.findByUserEmail(SecurityUtil.getCurrentUsername().get())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        AddressEntity addressEntity = addressRepository.findById(deliveryDto.getAddress().getAddressId()).get();

        if(addressEntity.getUser().getUserId() == userEntity.getUserId()){
            OrderEntity orderEntity = OrderEntity.builder()
                    .orderId(deliveryDto.getOrder().getOrderId())
                    .build();

            DeliveryEntity deliveryEntity = DeliveryEntity.builder()
                    .user(userEntity)
                    .address(addressEntity)
                    .order(orderEntity)
                    .deliveryStatus(DeliveryStatus.READY)
                    .build();

            deliveryRepository.save(deliveryEntity);
            return 1;
        }

        return 0;
    }
}
