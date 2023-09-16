package laundry.daeseda.controller;

import laundry.daeseda.dto.member.MemberDto;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/save")
    public ResponseEntity<List<String>> getCreate() { //register 호출
        String result = "memberName memberPhone memberEmail memberPassword";
        String[] memberArray = result.split(" ");
        List<String> memberList = new ArrayList<>();
        for(String n : memberArray){
            memberList.add(n);
        }
        return ResponseEntity.ok().body(memberList);
    }

    @PostMapping("/save")
    public ResponseEntity<String> createMember(@RequestBody MemberDto member) { //register 호출
        String message = "ok";
        System.out.println(member.getMemberNickname());
        System.out.println(member.getMemberPhone());
        System.out.println(member.getMemberName());
        System.out.println(member.getMemberEmail());
        System.out.println(member.getMemberPassword());
        return ResponseEntity.ok().body(message);
    }
}
