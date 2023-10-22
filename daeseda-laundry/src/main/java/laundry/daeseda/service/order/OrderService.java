package laundry.daeseda.service.order;

import laundry.daeseda.dto.order.OrderAllDto;
import laundry.daeseda.dto.order.OrderDto;
import laundry.daeseda.dto.order.OrderFormDto;
import laundry.daeseda.dto.order.OrderRequestDto;

import java.util.List;

public interface OrderService {

    OrderFormDto getOrderForm();

    void requestOrder(OrderDto orderDto);

    void withdrawOrder(OrderRequestDto orderRequestDto);

    void getOrderDetail();

    List<OrderAllDto> getUserOrderList();

    void patchStatus(OrderRequestDto order);
}
