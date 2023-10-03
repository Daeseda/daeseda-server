package laundry.daeseda.controller;

import laundry.daeseda.dto.order.OrderDto;
import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/request")
    public ResponseEntity<String> signupUser(@RequestBody @Valid OrderDto orderDto) {
        orderService.requestOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("r");
    }

}
