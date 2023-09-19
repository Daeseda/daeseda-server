package laundry.daeseda.service.user;

import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.entity.user.UserEntity;
import laundry.daeseda.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public int register(UserDto userDto) {
        if(userRepository.save(dtoToEntity(userDto)) != null)
            return 1;
        else
            return 0;
    }

    @Override
    public int delete(Long userId) {
        userRepository.deleteById(userId);
        return 1;
    }
}
