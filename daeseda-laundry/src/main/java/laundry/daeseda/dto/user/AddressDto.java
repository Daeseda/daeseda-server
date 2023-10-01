package laundry.daeseda.dto.user;

import laundry.daeseda.entity.user.UserEntity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Column;

@Getter

@Builder
public class AddressDto {
    private String addressName;
    private String addressDetail;
    private String addressZipcode;
}
