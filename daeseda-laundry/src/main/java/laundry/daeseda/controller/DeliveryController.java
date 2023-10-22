package laundry.daeseda.controller;

import laundry.daeseda.dto.delivery.DeliveryAllDto;
import laundry.daeseda.dto.delivery.DeliveryDto;
import laundry.daeseda.dto.order.OrderRequestDto;
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

    @GetMapping("/tracking")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<DeliveryAllDto> getTracking(@RequestParam(name = "order") Long orderId) {
        DeliveryAllDto deliveryAllDto = deliveryService.getDeliveryTrackingHistory(orderId);

        if(deliveryAllDto != null){
            return ResponseEntity.status(HttpStatus.OK).body(deliveryAllDto);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PostMapping("/request")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> requestDelivery(@RequestBody @Valid DeliveryDto deliveryDto) {
        if (deliveryService.requestDelivery(deliveryDto) > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("배송 요청이 완료되었습니다");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("등록된 주소가 아니거나 배송 신청 완료된 건입니다");
    }

    @PatchMapping("/start")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> changeStatus(@RequestBody @Valid OrderRequestDto order){

        return ResponseEntity.status(HttpStatus.OK).body("해줬음");
    }
}
