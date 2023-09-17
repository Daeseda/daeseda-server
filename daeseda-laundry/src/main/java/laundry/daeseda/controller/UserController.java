package laundry.daeseda.controller;

import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class UserController {

    private final UserService userService;

    @GetMapping("/save")
    public ResponseEntity<List<String>> getCreate() { //register 호출
        String result = "userNickname userName userPhone userEmail userPassword";
        String[] userArray = result.split(" ");
        List<String> userList = new ArrayList<>();
        for(String n : userArray){
            userList.add(n);
        }
        return ResponseEntity.ok().body(userList);
    }

    @PostMapping("/save")
    public ResponseEntity<String> createMember(@RequestBody UserDto userDto) { //register 호출
        String message = "ok";
        System.out.println(userDto.getUserNickname());
        System.out.println(userDto.getUserPhone());
        System.out.println(userDto.getUserName());
        System.out.println(userDto.getUserEmail());
        System.out.println(userDto.getUserPassword());
        userService.register(userDto);
        return ResponseEntity.ok().body(message);
    }
}
