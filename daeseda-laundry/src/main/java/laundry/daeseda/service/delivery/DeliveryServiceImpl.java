package laundry.daeseda.service.delivery;

import laundry.daeseda.constant.DeliveryStatus;
import laundry.daeseda.dto.delivery.DeliveryAllDto;
import laundry.daeseda.dto.delivery.DeliveryDto;
import laundry.daeseda.dto.order.OrderAllDto;
import laundry.daeseda.dto.order.OrderRequestDto;
import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.entity.delivery.DeliveryEntity;
import laundry.daeseda.entity.order.OrderEntity;
import laundry.daeseda.entity.user.UserEntity;
import laundry.daeseda.repository.delivery.DeliveryRepository;
import laundry.daeseda.repository.order.OrderRepository;
import laundry.daeseda.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService{

    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;

    @Override
    public DeliveryAllDto getDeliveryTrackingHistory(Long orderId) {
        UserEntity userEntity = userService.getUserEntity();
        OrderEntity orderEntity = orderRepository.findById(orderId).get();
        DeliveryEntity deliveryEntity = deliveryRepository.getByOrder(orderEntity);

        if(deliveryEntity != null){
            UserDto userDto = UserDto.builder()
                    .userEmail(userEntity.getUserEmail())
                    .userName(userEntity.getUserName())
                    .userNickname(userEntity.getUserNickname())
                    .userPhone(userEntity.getUserPhone())
                    .build();

            OrderAllDto orderAllDto = OrderAllDto.builder()
                    .orderId(orderEntity.getOrderId())
                    .address(orderEntity.getAddress())
                    .user(userDto)
                    .build();

            DeliveryAllDto deliveryAllDto = DeliveryAllDto.builder()
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
            UserEntity userEntity = userService.getUserEntity();

            DeliveryEntity deliveryEntity = DeliveryEntity.builder()
                    .user(userEntity)
                    .address(deliveryDto.getOrder().getAddress())
                    .order(orderEntity)
                    .deliveryStatus(DeliveryStatus.READY)
                    .build();
            deliveryRepository.save(deliveryEntity);

            return 1;
        }

        return 0;
    }

    @Transactional
    public void patchStartStatus(OrderRequestDto order) {
        DeliveryStatus deliveryStatus = DeliveryStatus.START;
        OrderEntity orderEntity = orderRepository.getById(order.getOrderId());
        deliveryRepository.updateStatus(deliveryStatus, orderEntity);
    }

    @Transactional
    public void patchEndStatus(OrderRequestDto order) {
        DeliveryStatus deliveryStatus = DeliveryStatus.END;
        OrderEntity orderEntity = orderRepository.getById(order.getOrderId());
        deliveryRepository.updateStatus(deliveryStatus, orderEntity);
    }
}
