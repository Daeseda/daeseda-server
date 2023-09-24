package laundry.daeseda.service.user;

import laundry.daeseda.dto.user.TokenDto;
import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.entity.user.UserEntity;

public interface UserService {
    int signup(UserDto userDto);
    int signout();
    UserDto getMyUserWithAuthorities();
    UserDto getUserWithAuthorities(String userEmail);
    int delete();
    int update(UserDto userDto);

    default UserEntity dtoToEntity(UserDto userDto){
        UserEntity entity = UserEntity.builder()
                .userName(userDto.getUserName())
                .userNickname(userDto.getUserNickname())
                .userPhone(userDto.getUserPhone())
                .userEmail(userDto.getUserEmail())
                .userPassword(userDto.getUserPassword())
                .build();
        return entity;
    }

    default UserDto entityToDto(UserEntity userEntity){
        UserDto dto = UserDto.builder()
                .userName(userEntity.getUserName())
                .userNickname(userEntity.getUserNickname())
                .userPhone(userEntity.getUserPhone())
                .userEmail(userEntity.getUserEmail())
                .userPassword(userEntity.getUserPassword())
                .build();
        return dto;
    }

}
