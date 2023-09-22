package laundry.daeseda.service.user;

import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.entity.user.AuthorityEntity;
import laundry.daeseda.entity.user.UserEntity;
import laundry.daeseda.exception.DuplicateUserException;
import laundry.daeseda.exception.NotFoundUserException;
import laundry.daeseda.repository.user.UserRepository;
import laundry.daeseda.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
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
    // BCryptPasswordEncoder 암호화 추가 예정

    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String userName) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUserName(userName).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        System.out.println(SecurityUtil.getCurrentUsername().get());
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUserName)
                        .orElseThrow(() -> new NotFoundUserException("User not found"))
        );
    }

    @Override
    public int update(UserDto userDto) {
        UserEntity userEntity = dtoToEntity(userDto);
        if(userRepository.findById(userEntity.getUserId()) != null){
            userRepository.save(userEntity);
            return 1;
        }
        else
            return 0;
    }

    @Override
    public int delete(Long userId) {
        try {
            userRepository.deleteById(userId);
            return 1;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

}
