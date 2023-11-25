package laundry.daeseda.user;

import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.entity.user.AuthorityEntity;
import laundry.daeseda.entity.user.UserEntity;
import laundry.daeseda.exception.DuplicateUserException;
import laundry.daeseda.perf.CompareTimer;
import laundry.daeseda.service.user.UserService;;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.stream.IntStream;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void signupUser() {
        CompareTimer timer = new CompareTimer();
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
                        timer.checkNanoTime();
                    } else {
                        timer.checkNanoTime();
                        System.out.println("등록 실패: " + i);
                    }
                });
    }

}
