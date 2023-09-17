package laundry.daeseda.service.user;

import laundry.daeseda.dto.user.UserDto;
import laundry.daeseda.entity.user.UserEntity;

public interface UserService {
    int register(UserDto userDto);

    default UserEntity dtoToEntity(UserDto userDto){
        UserEntity entity = UserEntity.builder()
                .id(userDto.getUserId())
                .name(userDto.getUserName())
                .nickname(userDto.getUserNickname())
                .phone(userDto.getUserPhone())
                .email(userDto.getUserEmail())
                .password(userDto.getUserPassword())
                .build();
        return entity;
    }

    default UserDto entityToDto(UserEntity userEntity){
        UserDto dto = UserDto.builder()
                .userId(userEntity.getId())
                .userName(userEntity.getName())
                .userNickname(userEntity.getNickname())
                .userPhone(userEntity.getPhone())
                .userEmail(userEntity.getEmail())
                .userPassword(userEntity.getPassword())
                .build();
        return dto;
    }
}
