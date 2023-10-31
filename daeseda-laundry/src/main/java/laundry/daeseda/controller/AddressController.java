package laundry.daeseda.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import laundry.daeseda.dto.address.AddressDto;
import laundry.daeseda.service.user.AddressService;
import laundry.daeseda.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"Address API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/address")
public class AddressController {

    private final AddressService addressService;
    private final UserService userService;

    @ApiOperation(value = "request setting-address", notes = "특정 주소 기본 배송지 설정")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/setting")
    public ResponseEntity<String> settingAddress(@RequestBody @Valid AddressDto addressDto) {
        if(userService.settingDefaultAddress(addressDto)){
            return ResponseEntity.ok().body(" 기본 배송지 설정 완료 ");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" 실패 ");
    }

    @ApiOperation(value = "request create-address", notes = "회원 주소 생성")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<String> createAddress(@RequestBody @Valid AddressDto addressDto) { //register 호출
        if (addressService.createAddress(addressDto) > 0) {
            return ResponseEntity.ok().body(" Success ");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(" fail ");
        }
    }

    @ApiOperation(value = "get address-list", notes = "회원 주소 목록 불러오기")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public List<AddressDto> getAddressList() {
        List<AddressDto> addressList = addressService.getMyAddressList(); // 주소 목록을 가져오는 비즈니스 로직 호출
        return addressList;
    }

    @ApiOperation(value = "request address-delete", notes = "회원 주소 삭제")
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> deleteAddress(@RequestBody @Valid AddressDto addressDto) {
        if (addressService.delete(addressDto) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Address deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found.");
        }
    }

}
