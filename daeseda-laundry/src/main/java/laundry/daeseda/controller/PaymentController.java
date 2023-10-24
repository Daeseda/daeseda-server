package laundry.daeseda.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import laundry.daeseda.dto.payment.PaymentAllDto;
import laundry.daeseda.dto.payment.PaymentDto;
import laundry.daeseda.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"Payment API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/order/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @ApiOperation(value = "get pay-list", notes = "전체 결제내역 불러오기(미구현)")
    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<PaymentAllDto>> getPayment(){
        return ResponseEntity.ok(paymentService.getPaymentList());

    }

    @ApiOperation(value = "request payment", notes = "결제 완료 등록 및 주문 상태 결제 완료로 변경")
    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> registrationPayment(@RequestBody @Valid PaymentDto paymentDto){
        paymentService.registrationPayment(paymentDto);
        return ResponseEntity.status(HttpStatus.OK).body("등록 완료");
    }
}
