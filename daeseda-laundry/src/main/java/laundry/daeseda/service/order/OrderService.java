package laundry.daeseda.service.order;

import laundry.daeseda.dto.order.OrderAllDto;
import laundry.daeseda.dto.order.OrderDto;
import laundry.daeseda.dto.order.OrderFormDto;
import laundry.daeseda.dto.order.OrderWithdrawDto;
import laundry.daeseda.dto.user.UserDto;

import java.util.List;

public interface OrderService {

    OrderFormDto getOrderForm();

    void requestOrder(OrderDto orderDto);

    void withdrawOrder(OrderWithdrawDto orderWithdrawDto);

    void getOrderDetail();

    List<OrderAllDto> getUserOrderList();
}
