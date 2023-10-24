package laundry.daeseda.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import laundry.daeseda.dto.user.LoginDto;
import laundry.daeseda.dto.user.TokenDto;
import laundry.daeseda.jwt.JwtFilter;
import laundry.daeseda.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = {"Login API"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class AuthController {
    private final TokenProvider tokenProvider; // 토큰 생성자
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisTemplate<String, Object> redisTemplate;

    @ApiOperation(value = "login", notes = "토큰 생성 및 발급 후 로그인 정보 redis 저장, 로그인")
    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUserEmail(), loginDto.getUserPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        redisTemplate.opsForValue().set("JWT_TOKEN:" + loginDto.getUserEmail(), jwt);
        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

}
