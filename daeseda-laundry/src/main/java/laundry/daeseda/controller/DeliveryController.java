package laundry.daeseda.controller;

import laundry.daeseda.dto.delivery.DeliveryDto;
import laundry.daeseda.service.delivery.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/tracking-list")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> getTracking() {
        deliveryService.getDeliveryTrackingList();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("주문 내역이 없습니다");
    }

    @PostMapping("/request")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> requestDelivery(@RequestBody DeliveryDto deliveryDto) {
        System.out.println("DeliveryController.requestDelivery");
        deliveryService.requestDelivery(deliveryDto);
        return ResponseEntity.status(HttpStatus.OK).body("배송 요청이 완료되었습니다");
    }
}
