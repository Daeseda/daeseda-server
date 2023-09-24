package laundry.daeseda.service.user;

import laundry.daeseda.dto.user.TokenDto;
import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.entity.user.AuthorityEntity;
import laundry.daeseda.entity.user.UserEntity;
import laundry.daeseda.exception.DuplicateUserException;
import laundry.daeseda.exception.NotFoundUserException;
import laundry.daeseda.jwt.TokenProvider;
import laundry.daeseda.repository.user.UserRepository;
import laundry.daeseda.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public int signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUserEmail(userDto.getUserEmail()).orElse(null) != null) {
            throw new DuplicateUserException("이미 가입되어 있는 유저입니다.");
        }

        AuthorityEntity authorityEntity = AuthorityEntity.builder()
                .authorityName("ROLE_USER")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .userNickname(userDto.getUserNickname())
                .userName(userDto.getUserName())
                .userPhone(userDto.getUserPhone())
                .userEmail(userDto.getUserEmail())
                .userPassword(passwordEncoder.encode(userDto.getUserPassword()))
                .authorities(Collections.singleton(authorityEntity))
                .activated(true)
                .build();

        if(userRepository.save(userEntity) != null)
            return 1;
        else
            return 0;
    }

    @Transactional
    public int signout() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (redisTemplate.opsForValue().get("JWT_TOKEN:" + userDetails.getUsername()) != null) {
//            redisTemplate.delete("JWT_TOKEN:" + userDetails.getUsername()); //Token 삭제
//        }
        try {
            redisTemplate.delete("JWT_TOKEN:" + userDetails.getUsername()); //Token 삭제
            return 1;
        } catch (NullPointerException n) {
            return 0;
        }
    }


    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String userName) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUserName(userName).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        System.out.println(SecurityUtil.getCurrentUsername().get());
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUserEmail)
                        .orElseThrow(() -> new NotFoundUserException("User not found"))
        );
    }

    @Transactional
    public int update(UserDto userDto) {
        Long id = userRepository.findByUserEmail(SecurityUtil.getCurrentUsername().get()).get().getUserId();
        UserEntity userEntity = UserEntity.builder()
                .userId(id)
                .userNickname(userDto.getUserNickname())
                .userName(userDto.getUserName())
                .userPhone(userDto.getUserPhone())
                .userEmail(userDto.getUserEmail())
                .userPassword(passwordEncoder.encode(userDto.getUserPassword()))
                .activated(true)
                .build();
        if(userRepository.findByUserEmail(SecurityUtil.getCurrentUsername().get()) != null){
            userRepository.save(userEntity);
            return 1;
        }
        else
            return 0;
    }

    @Transactional
    public int delete() {
        try {
            Optional<UserEntity> user = userRepository.findByUserEmail(SecurityUtil.getCurrentUsername().get());
            System.out.println(user.get().getUserId());
            userRepository.deleteById(user.get().getUserId());
            return 1;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

}
