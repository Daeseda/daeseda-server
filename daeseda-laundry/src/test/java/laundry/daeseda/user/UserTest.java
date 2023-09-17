package laundry.daeseda.user;

import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.service.user.UserService;;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void registerUser() {
        IntStream.rangeClosed(2, 101)
                .forEach(i -> {
                    UserDto userDto = UserDto.builder()
                            .userId(1L + i)
                            .userName("minwook" + i)
                            .userPhone("0105" + i)
                            .userNickname("min" + i)
                            .userEmail("email" + i + "@daeseda.com")
                            .userPassword("pw" + i)
                            .build();
                    if (userService.register(userDto) > 0) {
                        System.out.println("등록 성공: " + i);
                    } else {
                        System.out.println("등록 실패: " + i);
                    }
                });
    }
}
