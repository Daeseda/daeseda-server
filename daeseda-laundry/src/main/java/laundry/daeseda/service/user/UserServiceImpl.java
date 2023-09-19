package laundry.daeseda.service.user;

import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.entity.user.UserEntity;
import laundry.daeseda.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public int register(UserDto userDto) {
        if(userRepository.save(dtoToEntity(userDto)) != null)
            return 1;
        else
            return 0;
    }
    // BCryptPasswordEncoder 암호화 추가 예정

    @Override
    public UserDto read(Long userId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
        UserEntity userEntity = optionalUserEntity.orElseThrow(() -> new NoSuchElementException("해당 사용자가 없습니다."));
        return UserDto.builder()
                .userId(userEntity.getUserId())
                .userEmail(userEntity.getUserEmail())
                .userName(userEntity.getUserName())
                .userNickname(userEntity.getUserNickname())
                .build();
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
