package laundry.daeseda.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String userName;
    private String userPhone;
    private String userNickname;
    private String userEmail;
    private String userPassword;
}
