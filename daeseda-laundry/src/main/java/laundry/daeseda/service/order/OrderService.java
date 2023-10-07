package laundry.daeseda.service.order;

import laundry.daeseda.dto.order.OrderDto;
import laundry.daeseda.dto.order.OrderFormDto;
import laundry.daeseda.dto.user.UserDto;

public interface OrderService {

    OrderFormDto getOrderForm();

    void requestOrder(OrderDto orderDto);

}
