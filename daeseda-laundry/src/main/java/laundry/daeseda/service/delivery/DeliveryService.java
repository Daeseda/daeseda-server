package laundry.daeseda.service.delivery;

import laundry.daeseda.dto.delivery.DeliveryDto;

public interface DeliveryService {
    void getDeliveryTrackingList();
    int requestDelivery(DeliveryDto deliveryDto);
}
