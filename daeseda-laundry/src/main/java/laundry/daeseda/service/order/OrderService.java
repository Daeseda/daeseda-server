package laundry.daeseda.service.order;

import laundry.daeseda.dto.order.OrderDto;
import laundry.daeseda.dto.user.UserDto;

public interface OrderService {
    void requestOrder(OrderDto orderDto);
}
