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
    public void signupUser() {
        IntStream.rangeClosed(0, 10)
                .forEach(i -> {
                    UserDto userDto = UserDto.builder()
                            .userName("minwook" + i)
                            .userPhone("0105" + i)
                            .userNickname("min" + i)
                            .userEmail("email" + i + "@daeseda.com")
                            .userPassword("pw" + i)
                            .build();
                    if (userService.signup(userDto) > 0) {
                        System.out.println("등록 성공: " + i);
                    } else {
                        System.out.println("등록 실패: " + i);
                    }
                });
    }


}
