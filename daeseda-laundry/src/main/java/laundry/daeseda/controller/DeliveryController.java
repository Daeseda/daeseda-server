package laundry.daeseda.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(tags = {"Delivery API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @ApiOperation(value = "tracking delivery", notes = "배송 정보 출력")
    @GetMapping("/tracking")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<DeliveryAllDto> getTracking(@RequestParam(name = "order") Long orderId) {
        DeliveryAllDto deliveryAllDto = deliveryService.getDeliveryTrackingHistory(orderId);

        if(deliveryAllDto != null){
            return ResponseEntity.status(HttpStatus.OK).body(deliveryAllDto);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }


    @ApiOperation(value = "request delivery", notes = "배송 요청")
    @PostMapping("/request")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> requestDelivery(@RequestBody @Valid DeliveryDto deliveryDto) {
        if (deliveryService.requestDelivery(deliveryDto) > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("배송 요청이 완료되었습니다");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("등록된 주소가 아니거나 배송 신청 완료된 건입니다");
    }

    @ApiOperation(value = "start delivery", notes = "배송 시작 상태로 변경")
    @PatchMapping("/start")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> changeStartStatus(@RequestBody @Valid OrderRequestDto order){
        deliveryService.patchStartStatus(order);
        return ResponseEntity.status(HttpStatus.OK).body("해줬음");
    }

    @ApiOperation(value = "end delivery", notes = "배송 완료 상태로 변경")
    @PatchMapping("/end")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> changeEndStatus(@RequestBody @Valid OrderRequestDto order){
        deliveryService.patchEndStatus(order);
        return ResponseEntity.status(HttpStatus.OK).body("해줬음");
    }

}
