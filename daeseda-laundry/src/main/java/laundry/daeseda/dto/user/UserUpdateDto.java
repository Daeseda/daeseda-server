package laundry.daeseda.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateDto {

    private String userName;
    private String userNickname;
    private String userPhone;
    
}
