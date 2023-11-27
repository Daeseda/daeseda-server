//package laundry.daeseda.user;
//
//import laundry.daeseda.dto.address.AddressDto;
//import laundry.daeseda.entity.user.UserEntity;
//import laundry.daeseda.service.user.AddressService;
//import laundry.daeseda.service.user.UserService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.stream.IntStream;
//import java.util.stream.LongStream;
//
//@SpringBootTest
//public class AddressTest {
//
//    @Autowired
//    private AddressService addressService;
//
//    @Autowired
//    private UserService userService;
//
//    @Test
//    public void createAddress() {
//        UserService userService = Mockito.mock(UserService.class);
//
//        UserEntity mockUser = UserEntity.builder()
//                .userId(1L)
//                .build(); // 가짜 사용자 객체 생성
//        Mockito.when(userService.getUserEntity()).thenReturn(mockUser);
//
//        LongStream.rangeClosed(1, 10)
//                .forEach(i -> {
//                    AddressDto addressDto = AddressDto.builder()
//                            .addressId(i)
//                            .addressName(i + " addressName")
//                            .addressDetail(i + " addressDetail")
//                            .addressZipcode(i + "addressZipcode")
//                            .addressRoad(i + " addressRoad")
//                            .build();
//                    addressService.createAddress(addressDto);
//                });
//
//    }
//
//}
