package laundry.daeseda.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import laundry.daeseda.dto.order.OrderAllDto;
import laundry.daeseda.dto.order.OrderDto;
import laundry.daeseda.dto.order.OrderFormDto;
import laundry.daeseda.dto.order.OrderRequestDto;
import laundry.daeseda.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"Order API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @ApiOperation(value = "get order-form", notes = "주문에 필요한 정보 불러오기")
    @GetMapping("/order-form")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<OrderFormDto> orderForm() {
        return ResponseEntity.ok(orderService.getOrderForm());
    }

    @ApiOperation(value = "request order", notes = "주문 요청")
    @PostMapping("/request")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> orderRequest(@RequestBody @Valid OrderDto orderDto) {
        orderService.requestOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("r");
    }

    @ApiOperation(value = "withdraw order", notes = "주문 취소")
    @DeleteMapping("/withdraw")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> orderWithdraw(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        orderService.withdrawOrder(orderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("r");
    }

    @ApiOperation(value = "get order-list", notes = "주문 목록 불러오기")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<OrderAllDto>> myOrderList() {
        return ResponseEntity.ok(orderService.getUserOrderList());
    }

    @ApiOperation(value = "pay order", notes = "주문 결제 상태로 변경")
    @PatchMapping("/cash")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> changeStatus(@RequestBody @Valid OrderRequestDto order){
        System.out.println("OrderController.changeStatus");
        orderService.patchStatus(order);
        return ResponseEntity.status(HttpStatus.OK).body("해줬음");
    }
}
