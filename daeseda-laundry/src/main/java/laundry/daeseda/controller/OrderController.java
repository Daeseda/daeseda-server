package laundry.daeseda.controller;

import laundry.daeseda.dto.order.OrderDto;
import laundry.daeseda.dto.order.OrderFormDto;
import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/order-form")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<OrderFormDto> orderForm() {
        return ResponseEntity.ok(orderService.getOrderForm());
    }

    @PostMapping("/request")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> orderRequest(@RequestBody @Valid OrderDto orderDto) {
        orderService.requestOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("r");
    }

}
