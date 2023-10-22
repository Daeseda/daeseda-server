package laundry.daeseda.controller;

import laundry.daeseda.dto.order.OrderAllDto;
import laundry.daeseda.dto.order.OrderWithdrawDto;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/order/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<PaymentAllDto>> getPayment(){
        return ResponseEntity.ok(paymentService.getPaymentList());

    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> registrationPayment(@RequestBody @Valid PaymentDto paymentDto){
        paymentService.registrationPayment(paymentDto);
        return ResponseEntity.status(HttpStatus.OK).body("등록 완료");
    }
}
