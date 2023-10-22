package laundry.daeseda.service.delivery;

import laundry.daeseda.dto.delivery.DeliveryAllDto;
import laundry.daeseda.dto.delivery.DeliveryDto;
import laundry.daeseda.dto.order.OrderRequestDto;

public interface DeliveryService {
    DeliveryAllDto getDeliveryTrackingHistory(Long orderId);
    int requestDelivery(DeliveryDto deliveryDto);
}
