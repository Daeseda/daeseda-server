package laundry.daeseda.controller;

import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public ResponseEntity<List<String>> getRegister() { //register 호출
        String result = "userNickname userName userPhone userEmail userPassword";
        String[] userArray = result.split(" ");
        List<String> userList = new ArrayList<>();
        for(String n : userArray){
            userList.add(n);
        }
        return ResponseEntity.ok().body(userList);
    }
    // HttpStatus.OK (200) - Get 요청

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserDto userDto) { //register 호출
        String message = "ok";
        userService.register(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }
    // HttpStatus.CREATED (201), HttpStatus.OK (200) - Post 요청

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> readUser(@PathVariable @Positive Long userId) {
        UserDto userDto = userService.read(userId);
        return ResponseEntity.ok().body(userDto);
    }
    // HttpStatus.OK (200) - Get 요청

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@RequestBody @Valid UserDto userDto) {
        if (userService.update(userDto) > 0) {
            return ResponseEntity.ok().body("User updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }
    // HttpStatus.OK (200)을 반환 - Put 요청(성공)
    // HttpStatus.NOT_FOUND (404) - Put 요청(실패)

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable @Positive Long userId) {
        if (userService.delete(userId) > 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }
    // HttpStatus.NO_CONTENT (204) - Delete 요청(성공)
    // HttpStatus.NOT_FOUND (404) - Delete 요청(실패)
}
