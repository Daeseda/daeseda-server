package laundry.daeseda.service.delivery;

import laundry.daeseda.dto.delivery.DeliveryDto;

public interface DeliveryService {
    void getDeliveryTrackingList();
    void requestDelivery(DeliveryDto deliveryDto);
}
